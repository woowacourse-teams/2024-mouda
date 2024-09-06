package mouda.backend.api.moim.business;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Chamyo;
import mouda.backend.core.domain.moim.Chat;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.core.domain.moim.MoimRole;
import mouda.backend.api.moim.exception.ChatErrorMessage;
import mouda.backend.api.moim.exception.ChatException;
import mouda.backend.api.moim.infrastructure.ChamyoRepository;
import mouda.backend.api.moim.infrastructure.ChatRepository;
import mouda.backend.api.moim.infrastructure.MoimRepository;
import mouda.backend.core.dto.moim.request.chat.ChatCreateRequest;
import mouda.backend.core.dto.moim.request.chat.DateTimeConfirmRequest;
import mouda.backend.core.dto.moim.request.chat.LastReadChatRequest;
import mouda.backend.core.dto.moim.request.chat.PlaceConfirmRequest;
import mouda.backend.core.dto.moim.response.chat.ChatFindDetailResponse;
import mouda.backend.core.dto.moim.response.chat.ChatFindUnloadedResponse;
import mouda.backend.core.dto.moim.response.chat.ChatPreviewResponse;
import mouda.backend.core.dto.moim.response.chat.ChatPreviewResponses;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatService {

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

		notificationService.notifyToMembers(NotificationType.NEW_CHAT, darakbangId, moim, darakbangMember);
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

		notificationService.notifyToMembers(NotificationType.MOIM_PLACE_CONFIRMED, darakbangId, moim, darakbangMember);
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

		notificationService.notifyToMembers(NotificationType.MOIM_TIME_CONFIRMED, darakbangId, moim, darakbangMember);
	}

	public ChatPreviewResponses findChatPreview(Long darakbangId, DarakbangMember darakbangMember) {
		List<ChatPreviewResponse> chatPreviews = chamyoRepository
			.findAllByDarakbangMemberIdAndMoim_DarakbangId(darakbangMember.getId(), darakbangId)
			.stream()
			.filter(chamyo -> chamyo.getMoim().isChatOpened())
			.sorted(getChatComparatorByLastCreatedAt())
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

	private Comparator<Chamyo> getChatComparatorByLastCreatedAt() {
		return Comparator.<Chamyo, LocalDateTime>comparing(chamyo ->
				chatRepository.findFirstByMoimIdOrderByIdDesc(chamyo.getMoim().getId())
					.map(chat -> LocalDateTime.of(chat.getDate(), chat.getTime()))
					.orElse(null), Comparator.nullsLast(Comparator.reverseOrder()))
			.thenComparing(chamyo -> chamyo.getMoim().getId(), Comparator.naturalOrder());
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
