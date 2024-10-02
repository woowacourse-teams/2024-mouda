package mouda.backend.chat.business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.ChatWithAuthor;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.ChatType;
import mouda.backend.moim.exception.ChatErrorMessage;
import mouda.backend.moim.exception.ChatException;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatRepository chatRepository;
	private final ChamyoRepository chamyoRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	public void createChatRoom(long darakbangId, ChatRoomType chatRoomType, long targetId) {
		ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
			.darakbangId(darakbangId)
			.targetId(targetId)
			.type(chatRoomType)
			.build();

		chatRoomRepository.save(chatRoomEntity);
	}

	public void createChat(long darakbangId, ChatCreateRequest request, DarakbangMember darakbangMember) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByIdAndDarakbangId(request.chatRoomId(), darakbangId)
			.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.MOIM_NOT_FOUND));// TODO: 예외 메시지 수정

		ChatRoomType type = chatRoomEntity.getType();

		boolean isParticipated = false;
		if (type == ChatRoomType.MOIM) {
			// 모임에 다락방멤버가 참여하고 있는지 검증한다.
			isParticipated = chamyoRepository.existsByMoimIdAndDarakbangMemberId(chatRoomEntity.getTargetId(), darakbangMember.getId());
		}
		if (type == ChatRoomType.BET) {
			// 안내면진다에 다락방멤버가 참여하고 있는지 검증한다.
			isParticipated = betDarakbangMemberRepository.existsByBetIdAndDarakbangMemberId(chatRoomEntity.getTargetId(), darakbangMember.getId());
		}

		if (isParticipated) {

			// 챗을 저장한다.
			ChatEntity chatEntity = ChatEntity.builder()
				.chatRoomId(request.chatRoomId())
				.content(request.content())
				.date(LocalDate.now())
				.time(LocalTime.now())
				.darakbangMember(darakbangMember)
				.chatType(ChatType.BASIC)
				.build();
			chatRepository.save(chatEntity);
		} else {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.NOT_PARTICIPANT_TO_SEND); // TODO: 메시지 바꿔
		}

		// 알림을 발생한다.
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long chatRoomId, DarakbangMember darakbangMember
	) {
		// Moim moim = moimFinder.read(moimId, darakbangId);
		// chamyoValidator.validateMemberChamyoMoim(moim, darakbangMember);
		//
		// Chats chats = chatFinder.readAllUnloadedChats(moimId, recentChatId);
		// List<ChatWithAuthor> chatWithAuthors = chats.getChatsWithAuthor(darakbangMember);
		//
		// return ChatFindUnloadedResponse.toResponse(chatWithAuthors);
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByIdAndDarakbangId(chatRoomId, darakbangId)
			.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.MOIM_NOT_FOUND));// TODO: 예외 메시지 수정

		ChatRoomType type = chatRoomEntity.getType();
		boolean isParticipated = false;
		if (type == ChatRoomType.MOIM) {
			// 모임에 다락방멤버가 참여하고 있는지 검증한다.
			isParticipated = chamyoRepository.existsByMoimIdAndDarakbangMemberId(chatRoomEntity.getTargetId(), darakbangMember.getId());
		}
		if (type == ChatRoomType.BET) {
			// 안내면진다에 다락방멤버가 참여하고 있는지 검증한다.
			isParticipated = betDarakbangMemberRepository.existsByBetIdAndDarakbangMemberId(chatRoomEntity.getTargetId(), darakbangMember.getId());
		}
		if (isParticipated) {
			List<ChatEntity> chats = chatRepository.findAllUnloadedChats(chatRoomId, recentChatId);
			List<ChatWithAuthor> chatWithAuthors = chats.stream()
				.map(chat -> new ChatWithAuthor(chat, chat.isMyMessage(darakbangMember.getId())))
				.toList();

			return ChatFindUnloadedResponse.toResponse(chatWithAuthors);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void confirmPlace(
		long darakbangId, PlaceConfirmRequest request, DarakbangMember darakbangMember
	) {
		// Moim moim = moimFinder.read(request.moimId(), darakbangId);
		// moimWriter.confirmPlace(moim, darakbangMember, request.place());
		//
		// Chat chat = request.toEntity(moim, darakbangMember);
		// chatWriter.save(chat);
		//
		// notificationService.notifyToMembers(NotificationType.MOIM_PLACE_CONFIRMED, darakbangId, moim, darakbangMember);
	}

	public void confirmDateTime(
		long darakbangId, DateTimeConfirmRequest request, DarakbangMember darakbangMember
	) {
		// Moim moim = moimFinder.read(request.moimId(), darakbangId);
		// moimWriter.confirmDateTime(moim, darakbangMember, request.date(), request.time());
		//
		// Chat chat = request.toEntity(moim, darakbangMember);
		// chatWriter.save(chat);
		//
		// notificationService.notifyToMembers(NotificationType.MOIM_TIME_CONFIRMED, darakbangId, moim, darakbangMember);
	}

	public ChatPreviewResponses findChatPreview(long darakbangId, DarakbangMember darakbangMember) {
		// ChatRooms chatRooms = chatRoomFinder.findAllOrderByLastChat(darakbangId, darakbangMember);
		// List<MoimChat> moimChats = chatRooms.getMoimChats();
		//
		// return ChatPreviewResponses.toResponse(moimChats);
		return null;
	}

	public void createLastChat(
		long darakbangId, long moimId, LastReadChatRequest lastReadChatRequest, DarakbangMember darakbangMember
	) {
		// Moim moim = moimFinder.read(moimId, darakbangId);
		// Chamyo chamyo = chamyoFinder.read(moim, darakbangMember);
		//
		// chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
	}

	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		// Moim moim = moimFinder.read(moimId, darakbangId);
		// moimWriter.openChatByMoimer(moim, darakbangMember);
	}
}
