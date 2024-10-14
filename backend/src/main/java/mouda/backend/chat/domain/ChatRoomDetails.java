package mouda.backend.chat.domain;

import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class ChatRoomDetails {
	private final long id;
	private final ChatRoomType chatRoomType;
	private final Attributes attributes;
	private final List<Participant> participants;

	public ChatRoomDetails(long id, ChatRoomType chatRoomType, Attributes attributes, List<Participant> participants) {
		this.id = id;
		this.chatRoomType = chatRoomType;
		this.attributes = attributes;
		this.participants = participants;
	}

	public String getTitle() {
		return (String)getAttributes().get("title");
	}

	public Map<String, Object> getAttributes() {
		return attributes.getAttributes();
	}
}
