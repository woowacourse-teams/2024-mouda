package mouda.backend.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.moim.domain.Moim;

@Entity
@Getter
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nickName;

	@ManyToOne(fetch = FetchType.LAZY)
	private Moim moim;

	@Builder
	public Member(String nickName) {
		this.nickName = nickName;
	}

	public void joinMoim(Moim moim) {
		this.moim = moim;
	}
}
