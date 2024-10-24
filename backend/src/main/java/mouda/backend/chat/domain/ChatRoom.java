package mouda.backend.chat.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ChatRoom {

	private final long id;
	private final long targetId;
	private final ChatRoomType type;
	private final LastChat lastChat;

	public ChatRoom(Long id, long targetId, ChatRoomType type, LastChat lastChat) {
		this.id = id;
		this.targetId = targetId;
		this.type = type;
		this.lastChat = lastChat;
	}

	public ChatRoom(Long id, long targetId, ChatRoomType type) {
		this(id, targetId, type, LastChat.empty());
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
