package mouda.backend.moim.domain;

import java.util.List;

import mouda.backend.darakbangmember.domain.DarakbangMember;

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
