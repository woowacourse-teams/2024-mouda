package mouda.backend.chat.domain;

import lombok.Getter;
import mouda.backend.chat.entity.ChatEntity;

@Getter
public class ChatWithAuthor {

	private final ChatEntity chat;
	private final Participant participant;
	private final boolean isMine;

	public ChatWithAuthor(ChatEntity chat, Participant participant, boolean isMine) {
		this.chat = chat;
		this.participant = participant;
		this.isMine = isMine;
	}
}
