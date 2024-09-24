package mouda.backend.moim.domain;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class ChatRoom implements Comparable<ChatRoom> {

	private final Chamyo chamyo;
	private final List<Chat> chats;
	private final int currentPeople;

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

	private LocalDateTime getLastChatDateTime() {
		return getLastChat().getDateTime();
	}

	private String getLastChatContent() {
		return getLastChat().getContent();
	}

	private Chat getLastChat() {
		return chats.get(chats.size() - 1);
	}

	@Override
	public int compareTo(ChatRoom that) {
		Comparator<ChatRoom> chatRoomComparator = Comparator.comparing(ChatRoom::getLastChatDateTime,
				Comparator.nullsLast(Comparator.reverseOrder()))
			.thenComparing(chatRoom -> chatRoom.chamyo.getMoim().getId());
		return chatRoomComparator.compare(this, that);
	}
}
