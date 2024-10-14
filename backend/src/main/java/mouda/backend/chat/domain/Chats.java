package mouda.backend.chat.domain;

import java.util.List;

import lombok.Getter;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Getter
public class Chats {

	private final List<ChatEntity> chats;

	public Chats(List<ChatEntity> chats) {
		this.chats = chats;
	}

	public List<ChatWithAuthor> getChatsWithAuthor(DarakbangMember darakbangMember) {
		return chats.stream()
			.map(chat -> new ChatWithAuthor(chat, new Participant(
				darakbangMember.getNickname(),
				darakbangMember.getProfile(),
				darakbangMember.getDescription()),
				chat.isMyMessage(darakbangMember.getId())))
			.toList();
	}
}
