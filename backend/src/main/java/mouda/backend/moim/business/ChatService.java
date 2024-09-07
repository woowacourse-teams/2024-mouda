package mouda.backend.moim.business;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.ChatErrorMessage;
import mouda.backend.moim.exception.ChatException;
import mouda.backend.moim.implement.finder.ChatFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.validator.MoimValidator;
import mouda.backend.moim.implement.writer.ChatWriter;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.ChatRepository;
import mouda.backend.moim.presentation.request.chat.ChatCreateRequest;
import mouda.backend.moim.presentation.request.chat.DateTimeConfirmRequest;
import mouda.backend.moim.presentation.request.chat.LastReadChatRequest;
import mouda.backend.moim.presentation.request.chat.PlaceConfirmRequest;
import mouda.backend.moim.presentation.response.chat.ChatFindDetailResponse;
import mouda.backend.moim.presentation.response.chat.ChatFindUnloadedResponse;
import mouda.backend.moim.presentation.response.chat.ChatPreviewResponse;
import mouda.backend.moim.presentation.response.chat.ChatPreviewResponses;
import mouda.backend.notification.business.NotificationService;
import mouda.backend.notification.domain.NotificationType;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final ChamyoRepository chamyoRepository;
	private final NotificationService notificationService;
	private final MoimValidator moimValidator;
	private final MoimFinder moimFinder;
	private final ChatFinder chatFinder;
	private final ChatWriter chatWriter;

	public void createChat(long darakbangId, ChatCreateRequest chatCreateRequest, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(chatCreateRequest.moimId(), darakbangId);
		findChamyoByMoimIdAndMemberId(chatCreateRequest.moimId(), darakbangMember.getId());

		Chat chat = chatCreateRequest.toEntity(moim, darakbangMember);
		chatRepository.save(chat);

		notificationService.notifyToMembers(NotificationType.NEW_CHAT, darakbangId, moim, darakbangMember);
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long moimId, DarakbangMember darakbangMember
	) {
		moimValidator.validateMoimExists(moimId, darakbangId);
		findChamyoByMoimIdAndMemberId(moimId, darakbangMember.getId());
		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}
		List<Chat> chats = chatFinder.findAll(moimId, recentChatId);

		return new ChatFindUnloadedResponse(chats.stream()
			.map(chat -> ChatFindDetailResponse.toResponse(chat, chat.isMyMessage(darakbangMember.getId())))
			.toList());
	}

	public void confirmPlace(
		long darakbangId, PlaceConfirmRequest placeConfirmRequest, DarakbangMember darakbangMember
	) {
		Moim moim = moimFinder.read(placeConfirmRequest.moimId(), darakbangId);
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
		Moim moim = moimFinder.read(dateTimeConfirmRequest.moimId(), darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(dateTimeConfirmRequest.moimId(), darakbangMember.getId());
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.MOIMER_CAN_CONFIRM_DATETIME);
		}

		Chat chat = dateTimeConfirmRequest.toEntity(moim, darakbangMember);
		moim.confirmDateTime(dateTimeConfirmRequest.date(), dateTimeConfirmRequest.time());
		chatRepository.save(chat);

		notificationService.notifyToMembers(NotificationType.MOIM_TIME_CONFIRMED, darakbangId, moim, darakbangMember);
	}

	public ChatPreviewResponses findChatPreview(long darakbangId, DarakbangMember darakbangMember) {
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
		String lastContent = chatFinder.findLastChatContent(chamyo.getMoim().getId());
		int currentPeople = chamyoRepository.countByMoim(chamyo.getMoim());
		String lastContent = lastChat.map(Chat::getContent).orElse("");

		return ChatPreviewResponse.toResponse(chamyo, currentPeople, lastContent);
	}

	private Comparator<Chamyo> getChatComparatorByLastCreatedAt() {
		return Comparator.<Chamyo, LocalDateTime>comparing(chamyo ->
				chatFinder.findLastChat(chamyo.getMoim().getId())
					.map(chat -> LocalDateTime.of(chat.getDate(), chat.getTime()))
					.orElse(null), Comparator.nullsLast(Comparator.reverseOrder()))
			.thenComparing(chamyo -> chamyo.getMoim().getId(), Comparator.naturalOrder());
	}

	public void createLastChat(
		long darakbangId, long moimId, LastReadChatRequest lastReadChatRequest, DarakbangMember darakbangMember
	) {
		moimValidator.validateMoimExists(moimId, darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, darakbangMember.getId());

		chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
	}

	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		Moim moim = moimFinder.read(moimId, darakbangId);
		Chamyo chamyo = findChamyoByMoimIdAndMemberId(moimId, darakbangMember.getId());
		if (chamyo.getMoimRole() == MoimRole.MOIMER) {
			moim.openChat();
		}
		if (chamyo.getMoimRole() != MoimRole.MOIMER) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NO_PERMISSION_OPEN_CHAT);
		}
	}

	private Chamyo findChamyoByMoimIdAndMemberId(long moimId, long darakbangMemberId) {
		return chamyoRepository.findByMoimIdAndDarakbangMemberId(moimId, darakbangMemberId)
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_FIND));
	}
}
