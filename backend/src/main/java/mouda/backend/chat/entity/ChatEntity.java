package mouda.backend.chat.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.chat.domain.Chat;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Entity
@Getter
@Table(name = "chat")
@NoArgsConstructor
public class ChatEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	private long chatRoomId;

	@ManyToOne(fetch = FetchType.LAZY)
	private DarakbangMember darakbangMember;

	private LocalDate date;

	private LocalTime time;

	@Enumerated(EnumType.STRING)
	private ChatType chatType;

	@Builder
	public ChatEntity(String content, long chatRoomId, DarakbangMember darakbangMember, LocalDate date, LocalTime time,
		ChatType chatType) {
		this.content = content;
		this.chatRoomId = chatRoomId;
		this.darakbangMember = darakbangMember;
		this.date = date;
		this.time = time;
		this.chatType = chatType;
	}

	public static ChatEntity empty() {
		return ChatEntity.builder()
			.content("")
			.build();
	}

	public LocalDateTime getDateTime() {
		if (date == null || time == null) {
			return null;
		}
		return LocalDateTime.of(date, time);
	}

	public Chat toChat() {
		return Chat.builder()
			.id(id)
			.darakbangMember(darakbangMember)
			.content(content)
			.chatType(chatType)
			.date(date)
			.time(time)
			.build();
	}
}
