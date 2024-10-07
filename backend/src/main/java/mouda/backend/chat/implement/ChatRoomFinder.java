package mouda.backend.chat.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.ChatPreview;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.infrastructure.ChamyoRepository;

@Component
@RequiredArgsConstructor
public class ChatRoomFinder {

	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChamyoRepository chamyoRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;
	private final ChatRoomFinder chatRoomFinder;

	public ChatRoomEntity read(long darakbangId, long chatRoomId, DarakbangMember darakbangMember) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByIdAndDarakbangId(chatRoomId, darakbangId)
			.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.CHATROOM_NOT_FOUND));

		ChatRoomType type = chatRoomEntity.getType();

		boolean isParticipated = false;
		if (type == ChatRoomType.MOIM) {
			// 모임에 다락방멤버가 참여하고 있는지 검증한다.
			isParticipated = chamyoRepository.existsByMoimIdAndDarakbangMemberId(chatRoomEntity.getTargetId(),
				darakbangMember.getId());

		}
		if (type == ChatRoomType.BET) {
			// 안내면진다에 다락방멤버가 참여하고 있는지 검증한다.
			isParticipated = betDarakbangMemberRepository.existsByBetIdAndDarakbangMemberId(
				chatRoomEntity.getTargetId(), darakbangMember.getId());
		}

		if (!isParticipated) {
			throw new ChatException(HttpStatus.FORBIDDEN, ChatErrorMessage.UNAUTHORIZED);
		}
		return chatRoomEntity;
	}

	public ChatRoomEntity readMoimChatRoom(long darakbangId, long chatRoomId) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByIdAndDarakbangId(chatRoomId, darakbangId)
			.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.CHATROOM_NOT_FOUND));

		ChatRoomType type = chatRoomEntity.getType();
		if (type.isNotMoim()) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_CHATROOM_TYPE);
		}
		return chatRoomEntity;
	}

	public ChatEntity readLastChatByMoimId(long moimId) {
		ChatRoomEntity chatRoom = chatRoomRepository.findByTargetId(moimId)
			.orElseThrow();

		return chatRepository.findFirstByChatRoomIdOrderByIdDesc(chatRoom.getId())
			.orElse(new ChatEntity());
	}

	public ChatPreview readByMyMoim(long moimId) {
		ChatEntity chatEntity = chatRoomFinder.readLastChatByMoimId(moimId);
		long lastReadChatId = chamyoRepository.findLastReadChatIdByMoimId(moimId);
		int currentPeople = chamyoRepository.countByMoimId(moimId);

		return ChatPreview.builder()
			.chatEntity(chatEntity)
			.lastReadChatId(lastReadChatId)
			.currentPeople(currentPeople)
			.build();
	}
}
