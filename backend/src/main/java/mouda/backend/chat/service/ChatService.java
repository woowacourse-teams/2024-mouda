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
import mouda.backend.common.RequiredDarakbangMoim;
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

	@RequiredDarakbangMoim
	public void createChat(Long darakbangId, Long moimId, ChatCreateRequest chatCreateRequest, DarakbangMember member) {
		Moim moim = findMoimByMoimId(moimId);
		findChamyoByMoimIdAndMemberId(moimId, member.getId());

		Chat chat = chatCreateRequest.toEntity(moim, member);
		chatRepository.save(chat);

		NotificationType notificationType = NotificationType.NEW_CHAT;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(member.getNickname()))
			.targetUrl(baseUrl + chatroomUrl + "/" + moim.getId())
			.build();

		List<Long> membersToSendNotification = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.map(chamyo -> chamyo.getMember().getId())
			.filter(memberId -> !Objects.equals(memberId, member.getId()))
			.toList();

		notificationService.notifyToMembers(notification, membersToSendNotification);
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(long recentChatId, long moimId, DarakbangMember member) {
		findMoimByMoimId(moimId);
		findChamyoByMoimIdAndMemberId(moimId, member.getId());
		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}

		List<ChatFindDetailResponse> chats = chatRepository.findAllUnloadedChats(moimId, recentChatId)
			.stream()
			.map(chat -> ChatFindDetailResponse.toResponse(chat, chat.isMyMessage(member.getId())))
			.toList();

		return new ChatFindUnloadedResponse(chats);
	}

	@RequiredDarakbangMoim
	public void confirmPlace(
		long darakbangId, long moimId, PlaceConfirmRequest placeConfirmRequest, DarakbangMember member
	) {
		Moim moim = findMoimByMoimId(moimId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, member.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_PLACE);
		}

		Chat chat = placeConfirmRequest.toEntity(moim, member);
		moim.confirmPlace(placeConfirmRequest.place());
		chatRepository.save(chat);

		sendNotificationWhenMoimPlaceOrTimeConfirmed(moim, NotificationType.MOIM_PLACE_CONFIRMED);
	}

	private void sendNotificationWhenMoimPlaceOrTimeConfirmed(Moim moim, NotificationType notificationType) {
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(moim.getTitle()))
			.targetUrl(baseUrl + moimUrl + "/" + moim.getId())
			.build();

		List<Long> membersToSendNotification = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.filter(chamyo -> chamyo.getMoimRole() != MoimRole.MOIMER)
			.map(chamyo -> chamyo.getMember().getId())
			.toList();

		notificationService.notifyToMembers(notification, membersToSendNotification);
	}

	@RequiredDarakbangMoim
	public void confirmDateTime(
		long darakbangId, long moimId, DateTimeConfirmRequest dateTimeConfirmRequest, DarakbangMember member
	) {
		Moim moim = findMoimByMoimId(moimId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, member.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_DATETIME);
		}

		Chat chat = dateTimeConfirmRequest.toEntity(moim, member);
		moim.confirmDateTime(dateTimeConfirmRequest.date(), dateTimeConfirmRequest.time());
		chatRepository.save(chat);

		sendNotificationWhenMoimPlaceOrTimeConfirmed(moim, NotificationType.MOIM_TIME_CONFIRMED);
	}

	public ChatPreviewResponses findChatPreview(Long darakbangId, DarakbangMember member) {
		List<ChatPreviewResponse> chatPreviews = chamyoRepository
			.findAllByMemberIdAndMoim_DarakbangId(member.getId(), darakbangId)
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

	@RequiredDarakbangMoim
	public void createLastChat(
		long darakbangId, long moimId, LastReadChatRequest lastReadChatRequest, DarakbangMember member
	) {
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, member.getId());

		chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
	}

	@RequiredDarakbangMoim
	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember member) {
		Moim moim = findMoimByMoimId(moimId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, member.getId());
		if (chamyo.getMoimRole() == MoimRole.MOIMER) {
			moim.openChat();
		}
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NO_PERMISSION_OPEN_CHAT);
		}
	}

	private Moim findMoimByMoimId(long moimId) {
		return moimRepository.findById(moimId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIM_NOT_FOUND));
	}

	private Chamyo findChamyoByMoimIdAndMemberId(long moimId, long memberId) {
		return chamyoRepository.findByMoimIdAndMemberId(moimId, memberId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_FIND));
	}
}
