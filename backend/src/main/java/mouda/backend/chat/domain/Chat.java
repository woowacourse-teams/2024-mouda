package mouda.backend.chat.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;

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
	private Member member;

	private LocalDate date;

	private LocalTime time;

	@Builder
	public Chat(String content, Moim moim, Member member, LocalDate date, LocalTime time) {
		this.content = content;
		this.moim = moim;
		this.member = member;
		this.date = date;
		this.time = time;
	}

	public boolean isMyMessage(long memberId) {
		return memberId == member.getId();
	}
}
