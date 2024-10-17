package mouda.backend.chat.domain;

import lombok.Getter;

@Getter
public class ChatOwnership {

	private final Chat chat;
	private final boolean isMine;

	public ChatOwnership(Chat chat, boolean isMine) {
		this.chat = chat;
		this.isMine = isMine;
	}
}
