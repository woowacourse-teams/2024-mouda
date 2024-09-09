package mouda.backend.moim.domain;

import java.util.List;

public class ChatRoom {

	Chamyo chamyo;
	List<Chat> chats;
	int currentPeople;

	public ChatRoom(Chamyo chamyo, Chat lastChat, int currentPeople) {
		this(chamyo, List.of(lastChat), currentPeople);
	}

	public ChatRoom(Chamyo chamyo, List<Chat> chats, int currentPeople) {
		this.chamyo = chamyo;
		this.chats = chats;
		this.currentPeople = currentPeople;
	}

	public MoimChat getMoimChat() {
		return MoimChat.builder()
			.chamyo(chamyo)
			.lastContent(getLastChatContent())
			.currentPeople(currentPeople)
			.build();
	}

	private String getLastChatContent() {
		return getLastChat().getContent();
	}

	private Chat getLastChat() {
		return chats.get(chats.size() - 1);
	}
}
