package mouda.backend.moim.domain;

import java.util.List;

public class ChatRooms {

	private final List<ChatRoom> chatRooms;

	public ChatRooms(List<ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}

	public List<MoimChat> getMoimChats() {
		return chatRooms.stream()
			.map(ChatRoom::getMoimChat)
			.toList();
	}
}
