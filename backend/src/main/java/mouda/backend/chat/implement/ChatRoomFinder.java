package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Chats;
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

	public ChatRoom read(long darakbangId, long chatRoomId, DarakbangMember darakbangMember) {
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
		return new ChatRoom(chatRoomEntity);
	}

	public ChatRoom readMoimChatRoom(long darakbangId, long chatRoomId) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByIdAndDarakbangId(chatRoomId, darakbangId)
			.orElseThrow(() -> new ChatException(HttpStatus.NOT_FOUND, ChatErrorMessage.CHATROOM_NOT_FOUND));

		ChatRoomType type = chatRoomEntity.getType();
		if (type.isNotMoim()) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_CHATROOM_TYPE);
		}
		return new ChatRoom(chatRoomEntity);
	}

	public ChatRoom readChatRoomByTargetId(long targetId) {
		ChatRoomEntity chatRoomEntity = chatRoomRepository.findByTargetId(targetId)
			.orElseThrow();

		ChatEntity lastChat = chatRepository.findFirstByChatRoomIdOrderByIdDesc(chatRoomEntity.getId())
			.orElse(ChatEntity.empty());

		return new ChatRoom(chatRoomEntity, lastChat);
	}

	public Chats findAllUnloadedChats(long chatRoomId, long recentChatId) {
		List<ChatEntity> chats = chatRepository.findAllUnloadedChats(chatRoomId, recentChatId);
		return new Chats(chats);
	}
}
