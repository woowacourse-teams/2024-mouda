package mouda.backend.chat.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import mouda.backend.chat.entity.ChatType;

@Getter
public class Chat {

	private final long id;
	private final String content;
	private final Author author;
	private final LocalDate date;
	private final LocalTime time;
	private final ChatType chatType;

	@Builder
	public Chat(long id, String content, Author author, LocalDate date, LocalTime time, ChatType chatType) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.date = date;
		this.time = time;
		this.chatType = chatType;
	}

	public boolean isMine(long darakbangMemberId) {
		return author.getDarakbangMemberId() == darakbangMemberId;
	}
}
