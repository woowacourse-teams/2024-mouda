package mouda.backend.chamyo.domain;

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
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;

@Entity
@Getter
@NoArgsConstructor
public class Chamyo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Moim moim;

	@ManyToOne
	private Member member;

	@Enumerated(EnumType.STRING)
	private MoimRole moimRole;

	@Builder
	public Chamyo(Moim moim, Member member, MoimRole moimRole) {
		this.moim = moim;
		this.member = member;
		this.moimRole = moimRole;
	}
}
