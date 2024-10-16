package mouda.backend.chat.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import mouda.backend.chat.entity.ChatType;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Getter
public class Chat {

	private final long id;
	private final String content;
	private final DarakbangMember darakbangMember;
	private final LocalDate date;
	private final LocalTime time;
	private final ChatType chatType;

	@Builder
	public Chat(long id, String content, DarakbangMember darakbangMember, LocalDate date, LocalTime time,
		ChatType chatType) {
		this.id = id;
		this.content = content;
		this.darakbangMember = darakbangMember;
		this.date = date;
		this.time = time;
		this.chatType = chatType;
	}

	public boolean isMyMessage(long darakbangMemberId) {
		return darakbangMember.getId() == darakbangMemberId;
	}
}
