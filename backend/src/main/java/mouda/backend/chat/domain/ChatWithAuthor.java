package mouda.backend.chat.domain;

import lombok.Getter;
import mouda.backend.chat.entity.ChatEntity;

@Getter
public class ChatWithAuthor {

	private final ChatEntity chat;
	private final boolean isMine;

	public ChatWithAuthor(ChatEntity chat, boolean isMine) {
		this.chat = chat;
		this.isMine = isMine;
	}
}
