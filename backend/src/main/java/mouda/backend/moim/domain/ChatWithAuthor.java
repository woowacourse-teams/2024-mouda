package mouda.backend.moim.domain;

import lombok.Getter;

@Getter
public class ChatWithAuthor {

	private final Chat chat;
	private final boolean isMine;

	public ChatWithAuthor(Chat chat, boolean isMine) {
		this.chat = chat;
		this.isMine = isMine;
	}
}
