package mouda.backend.chat.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.implement.ChatRoomFinder;
import mouda.backend.chat.implement.ChatWriter;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.chat.presentation.response.ChatPreviewResponse;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.moim.implement.writer.MoimWriter;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatRepository chatRepository;
	private final ChamyoRepository chamyoRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;
	private final MoimFinder moimFinder;
	private final ChatRoomFinder chatRoomFinder;
	private final ChatWriter chatWriter;
	private final MoimWriter moimWriter;

	public void createChatRoom(long darakbangId, ChatRoomType chatRoomType, long targetId) {
		// 채팅방을 생성한다.
		ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
			.darakbangId(darakbangId)
			.targetId(targetId)
			.type(chatRoomType)
			.build();

		chatRoomRepository.save(chatRoomEntity);
	}

	public void createChat(long darakbangId, ChatCreateRequest request, DarakbangMember darakbangMember) {
		ChatRoomEntity chatRoomEntity = chatRoomFinder.read(darakbangId, request.chatRoomId(), darakbangMember);

		chatWriter.appendPlaceTypeChat(chatRoomEntity.getId(), request.content(), darakbangMember);
		// 알림을 발생한다.
	}

	@Transactional(readOnly = true)
	public ChatFindUnloadedResponse findUnloadedChats(
		long darakbangId, long recentChatId, long chatRoomId, DarakbangMember darakbangMember
	) {
		ChatRoomEntity chatRoomEntity = chatRoomFinder.read(darakbangId, chatRoomId, darakbangMember);
		List<ChatEntity> chats = chatRepository.findAllUnloadedChats(chatRoomEntity.getId(), recentChatId);

		return ChatFindUnloadedResponse.toResponse(chats, darakbangMember.getId());
	}

	public void confirmPlace(long darakbangId, PlaceConfirmRequest request, DarakbangMember darakbangMember) {
		ChatRoomEntity chatRoomEntity = chatRoomFinder.readMoimChatRoom(darakbangId, request.chatRoomId());

		moimWriter.confirmPlace(chatRoomEntity.getTargetId(), darakbangMember, request.place());

		chatWriter.appendPlaceTypeChat(chatRoomEntity.getId(), request.place(), darakbangMember);
		// notificationService.notifyToMembers(NotificationType.MOIM_PLACE_CONFIRMED, darakbangId, moim, darakbangMember);
	}

	public void confirmDateTime(long darakbangId, DateTimeConfirmRequest request, DarakbangMember darakbangMember) {
		ChatRoomEntity chatRoomEntity = chatRoomFinder.readMoimChatRoom(darakbangId, request.chatRoomId());

		moimWriter.confirmDateTime(chatRoomEntity.getTargetId(), darakbangMember, request.date(), request.time());

		chatWriter.appendDateTimeTypeChat(chatRoomEntity.getId(), request.date(), request.time(), darakbangMember);

		// notificationService.notifyToMembers(NotificationType.MOIM_TIME_CONFIRMED, darakbangId, moim, darakbangMember);
	}

	public ChatPreviewResponses findChatPreview(long darakbangId, DarakbangMember darakbangMember,
		ChatRoomType chatRoomType) {

		// 내가 참여한 모임
		// 각 모임의 채팅룸
		// 채팅룸의 마지막 채팅
		if (chatRoomType == ChatRoomType.MOIM) {
			// 내가 참여한 모임
			List<Moim> moims = moimFinder.readAllMyMoims(darakbangMember);

			List<ChatPreviewResponse> chatPreviewResponses = new ArrayList<>();
			for (Moim moim : moims) {
				ChatPreview chatPreview = chatRoomFinder.readByMyMoim(moim.getId());
				chatPreviewResponses.add(ChatPreviewResponse.toResponse(moim, chatPreview));
			}

			return ChatPreviewResponses.toResponse(chatPreviewResponses);
		}

		if (chatRoomType == ChatRoomType.BET) {
			List<ChatPreviewResponse> chatPreviewResponses = new ArrayList<>();

			List<BetDarakbangMemberEntity> participatedBets = betDarakbangMemberRepository.findAllByDarakbangMemberId(
				darakbangMember.getId());

			for (BetDarakbangMemberEntity participatedBet : participatedBets) {
				BetEntity betEntity = participatedBet.getBet();
				ChatRoomEntity chatRoom = chatRoomRepository.findByTargetId(betEntity.getId()).orElseThrow();
				ChatEntity lastChatEntity = chatRepository.findFirstByChatRoomIdOrderByIdDesc(chatRoom.getId())
					.orElse(new ChatEntity());

				long participantSize = betDarakbangMemberRepository.countByBetId(betEntity.getId());

				chatPreviewResponses.add(
					ChatPreviewResponse.toResponse(betEntity, lastChatEntity, (int)participantSize,
						participatedBet.getLastReadChatId()));
			}

			return new ChatPreviewResponses(chatPreviewResponses);
		}
		throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_CHATROOM_TYPE);
	}

	public void updateLastReadChat(
		long darakbangId, long chatRoomId, LastReadChatRequest lastReadChatRequest, DarakbangMember darakbangMember
	) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByIdAndDarakbangId(chatRoomId, darakbangId)
			.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.CHATROOM_NOT_FOUND));

		ChatRoomType type = chatRoomEntity.getType();

		if (type == ChatRoomType.MOIM) {
			Chamyo chamyo = chamyoRepository.findByMoimIdAndDarakbangMemberId(
					chatRoomEntity.getTargetId(), darakbangMember.getId())
				.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.CHAMYO_NOT_FOUND));
			chamyo.updateLastChat(lastReadChatRequest.lastReadChatId());
		}
		if (type == ChatRoomType.BET) {
			BetDarakbangMemberEntity betDarakbangMemberEntity = betDarakbangMemberRepository
				.findByBetIdAndDarakbangMemberId(chatRoomEntity.getTargetId(), darakbangMember.getId())
				.orElseThrow(
					() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.BET_DARAKBANG_MEMBER_NOT_FOUND));
			betDarakbangMemberEntity.updateLastChat(lastReadChatRequest.lastReadChatId());
		}
	}

	public void openChatRoom(Long darakbangId, Long moimId, DarakbangMember darakbangMember) {
		boolean isParticipated = chamyoRepository.existsByMoimIdAndDarakbangMemberId(moimId, darakbangMember.getId());
		if (isParticipated) {
			ChatRoomEntity chatRoomEntity = new ChatRoomEntity(moimId, darakbangId, ChatRoomType.MOIM);
			chatRoomRepository.save(chatRoomEntity);
		}
	}
}
