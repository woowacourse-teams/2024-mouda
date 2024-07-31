package mouda.backend.zzim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;

@Entity
@Getter
@NoArgsConstructor
public class Zzim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Moim moim;

	@ManyToOne
	private Member member;
}
