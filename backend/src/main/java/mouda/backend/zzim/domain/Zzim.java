package mouda.backend.zzim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;

@Entity
@Getter
@NoArgsConstructor
public class Zzim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Moim moim;

	@ManyToOne
	@JoinColumn(nullable = false)
	private DarakbangMember member;

	@Builder
	public Zzim(Moim moim, DarakbangMember member) {
		this.moim = moim;
		this.member = member;
	}
}
