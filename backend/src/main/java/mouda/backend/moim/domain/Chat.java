package mouda.backend.moim.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Entity
@Getter
@NoArgsConstructor
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;

	@ManyToOne
	private Moim moim;

	@ManyToOne
	private DarakbangMember darakbangMember;

	private LocalDate date;

	private LocalTime time;

	@Enumerated(EnumType.STRING)
	private ChatType chatType;

	@Builder
	public Chat(String content, Moim moim, DarakbangMember darakbangMember, LocalDate date, LocalTime time,
		ChatType chatType) {
		this.content = content;
		this.moim = moim;
		this.darakbangMember = darakbangMember;
		this.date = date;
		this.time = time;
		this.chatType = chatType;
	}

	public boolean isMyMessage(long darakbangMemberId) {
		return darakbangMemberId == darakbangMember.getId();
	}
}
