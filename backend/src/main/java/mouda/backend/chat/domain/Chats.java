package mouda.backend.chat.domain;

import java.util.List;

import lombok.Getter;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Getter
public class Chats {

	private final List<Chat> chats;

	public Chats(List<Chat> chats) {
		this.chats = chats;
	}

	public List<ChatWithAuthor> getChatsWithAuthor(DarakbangMember darakbangMember) {
		return chats.stream()
			.map(chat -> new ChatWithAuthor(chat, chat.isMyMessage(darakbangMember.getId())))
			.toList();
	}
}
