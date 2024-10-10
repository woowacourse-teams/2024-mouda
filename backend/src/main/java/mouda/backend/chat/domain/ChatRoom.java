package mouda.backend.chat.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;

@Getter
public class ChatRoom {

	private final Long id;
	private final long targetId;
	private final ChatRoomType type;
	private final LastChat lastChat;

	public ChatRoom(ChatRoomEntity chatRoomEntity, ChatEntity lastChat) {
		this.id = chatRoomEntity.getId();
		this.targetId = chatRoomEntity.getTargetId();
		this.type = chatRoomEntity.getType();
		this.lastChat = new LastChat(lastChat.getDateTime(), lastChat.getContent());
	}

	public ChatRoom(ChatRoomEntity chatRoomEntity) {
		this.id = chatRoomEntity.getId();
		this.targetId = chatRoomEntity.getTargetId();
		this.type = chatRoomEntity.getType();
		this.lastChat = LastChat.empty();
	}

	public boolean isMoim() {
		return type == ChatRoomType.MOIM;
	}

	public boolean isBet() {
		return type == ChatRoomType.BET;
	}

	public LocalDateTime getLastChatDateTime() {
		return lastChat.getDateTime();
	}
}