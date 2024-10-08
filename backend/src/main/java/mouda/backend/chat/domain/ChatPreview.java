package mouda.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatPreview {

	private final ChatRoom chatRoom;
	private final Target target;
	private final long lastReadChatId;
	private final int currentPeople;

	@Builder
	public ChatPreview(ChatRoom chatRoom, Target target, long lastReadChatId, int currentPeople) {
		this.chatRoom = chatRoom;
		this.target = target;
		this.lastReadChatId = lastReadChatId;
		this.currentPeople = currentPeople;
	}

	public String getLastContent() {
		return chatRoom.getLastContent();
	}
}
