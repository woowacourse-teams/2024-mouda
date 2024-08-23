package mouda.backend.chat.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.DateTimeConfirmRequest;
import mouda.backend.chat.dto.request.LastReadChatRequest;
import mouda.backend.chat.dto.request.PlaceConfirmRequest;
import mouda.backend.chat.dto.response.ChatFindDetailResponse;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.repository.ChatRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.service.NotificationService;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatService {

	@Value("${url.base}")
	private String baseUrl;

	@Value("${url.moim}")
	private String moimUrl;

	@Value("${url.chatroom}")
	private String chatroomUrl;

	private final ChatRepository chatRepository;
	private final MoimRepository moimRepository;
	private final ChamyoRepository chamyoRepository;
	private final NotificationService notificationService;

	public void createChat(Long darakbangId, ChatCreateRequest chatCreateRequest, DarakbangMember darakbangMember) {
		Moim moim = findMoimByMoimId(chatCreateRequest.moimId(), darakbangId);
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND);
		}
		findChamyoByMoimIdAndMemberId(chatCreateRequest.moimId(), darakbangMember.getId());

		Chat chat = chatCreateRequest.toEntity(moim, darakbangMember);
		chatRepository.save(chat);

		NotificationType notificationType = NotificationType.NEW_CHAT;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(darakbangMember.getNickname()))
			.targetUrl(baseUrl + String.format(chatroomUrl, darakbangId, moim.getId()))
			.build();

		List<Long> membersToSendNotification = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.map(chamyo -> chamyo.getDarakbangMember().getMemberId())
			.filter(memberId -> !Objects.equals(memberId, darakbangMember.getMemberId()))
			.toList();

		notificationService.notifyToMembers(notification, membersToSendNotification, darakbangId);
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long moimId, DarakbangMember darakbangMember
	) {
		findMoimByMoimId(moimId, darakbangId);
		findChamyoByMoimIdAndMemberId(moimId, darakbangMember.getId());
		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}

		List<ChatFindDetailResponse> chats = chatRepository.findAllUnloadedChats(moimId, recentChatId)
			.stream()
			.map(chat -> ChatFindDetailResponse.toResponse(chat, chat.isMyMessage(darakbangMember.getId())))
			.toList();

		return new ChatFindUnloadedResponse(chats);
	}

	public void confirmPlace(
		long darakbangId, PlaceConfirmRequest placeConfirmRequest, DarakbangMember darakbangMember
	) {
		Moim moim = findMoimByMoimId(placeConfirmRequest.moimId(), darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(placeConfirmRequest.moimId(), darakbangMember.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_PLACE);
		}

		Chat chat = placeConfirmRequest.toEntity(moim, darakbangMember);
		moim.confirmPlace(placeConfirmRequest.place());
		chatRepository.save(chat);

		sendNotificationWhenMoimPlaceOrTimeConfirmed(moim, NotificationType.MOIM_PLACE_CONFIRMED, darakbangId);
	}

	private void sendNotificationWhenMoimPlaceOrTimeConfirmed(
		Moim moim, NotificationType notificationType, Long darakbangId
	) {
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(moim.getTitle()))
			.targetUrl(baseUrl + String.format(moimUrl, darakbangId, moim.getId()))
			.build();

		List<Long> membersToSendNotification = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.filter(chamyo -> chamyo.getMoimRole() != MoimRole.MOIMER)
			.map(chamyo -> chamyo.getDarakbangMember().getMemberId())
			.toList();

		notificationService.notifyToMembers(notification, membersToSendNotification, darakbangId);
	}

	public void confirmDateTime(
		long darakbangId, DateTimeConfirmRequest dateTimeConfirmRequest, DarakbangMember darakbangMember
	) {
		Moim moim = findMoimByMoimId(dateTimeConfirmRequest.moimId(), darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(dateTimeConfirmRequest.moimId(), darakbangMember.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_DATETIME);
		}

		Chat chat = dateTimeConfirmRequest.toEntity(moim, darakbangMember);
		moim.confirmDateTime(dateTimeConfirmRequest.date(), dateTimeConfirmRequest.time());
		chatRepository.save(chat);

		sendNotificationWhenMoimPlaceOrTimeConfirmed(moim, NotificationType.MOIM_TIME_CONFIRMED, darakbangId);
	}

	public ChatPreviewResponses findChatPreview(Long darakbangId, DarakbangMember darakbangMember) {
		List<ChatPreviewResponse> chatPreviews = chamyoRepository
			.findAllByDarakbangMemberIdAndMoim_DarakbangId(darakbangMember.getId(), darakbangId)
			.stream()
			.filter(chamyo -> chamyo.getMoim().isChatOpened())
			.map(this::getChatPreviewResponse)
			.toList();

		return new ChatPreviewResponses(chatPreviews);
	}

	private ChatPreviewResponse getChatPreviewResponse(Chamyo chamyo) {
		Optional<Chat> lastChat = chatRepository.findFirstByMoimIdOrderByIdDesc(
			chamyo.getMoim().getId());

		int currentPeople = chamyoRepository.countByMoim(chamyo.getMoim());
		String lastContent = lastChat.map(Chat::getContent).orElse("");

		return ChatPreviewResponse.toResponse(chamyo, currentPeople, lastContent);
	}

	public void createLastChat(
		long darakbangId, long moimId, LastReadChatRequest lastReadChatRequest, DarakbangMember darakbangMember
	) {
		findMoimByMoimId(moimId, darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, darakbangMember.getId());

		chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
	}

	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = findMoimByMoimId(moimId, darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, darakbangMember.getId());
		if (chamyo.getMoimRole() == MoimRole.MOIMER) {
			moim.openChat();
		}
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NO_PERMISSION_OPEN_CHAT);
		}
	}

	private Moim findMoimByMoimId(long moimId, long darabangId) {
		Moim moim = moimRepository.findById(moimId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND));
		if (moim.isNotInDarakbang(darabangId)) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND);
		}
		return moim;
	}

	private Chamyo findChamyoByMoimIdAndMemberId(long moimId, long darakbangMemberId) {
		return chamyoRepository.findByMoimIdAndDarakbangMemberId(moimId, darakbangMemberId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_FIND));
	}
}
