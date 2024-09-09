package mouda.backend.moim.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class ChatRooms {

	List<ChatRoom> chatRooms;

	public ChatRooms(List<ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}

	public List<MoimChat> getMoimChats() {
		return chatRooms.stream()
			.map(ChatRoom::getMoimChat)
			.toList();
	}
}
