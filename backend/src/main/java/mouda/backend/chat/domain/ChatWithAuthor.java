package mouda.backend.chat.domain;

import lombok.Getter;

@Getter
public class ChatWithAuthor {

	private final Chat chat;
	private final Participant participant;
	private final boolean isMine;

	public ChatWithAuthor(Chat chat, Participant participant,boolean isMine) {
		this.chat = chat;
		this.participant = participant;
		this.isMine = isMine;
	}
}
