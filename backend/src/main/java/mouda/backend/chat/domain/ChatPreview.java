package mouda.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;
import mouda.backend.chat.entity.ChatEntity;

@Getter
public class ChatPreview {

	private final ChatEntity chatEntity;
	private final long lastReadChatId;
	private final int currentPeople;

	@Builder
	public ChatPreview(ChatEntity chatEntity, long lastReadChatId, int currentPeople) {
		this.chatEntity = chatEntity;
		this.lastReadChatId = lastReadChatId;
		this.currentPeople = currentPeople;
	}

	public String getLastContent() {
		return chatEntity.getContent();
	}
}
