package mouda.backend.moim.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Entity
@Table(name = "zzim")
@Getter
@NoArgsConstructor
public class Zzim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Moim moim;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private DarakbangMember darakbangMember;

	@Builder
	public Zzim(Moim moim, DarakbangMember darakbangMember) {
		this.moim = moim;
		this.darakbangMember = darakbangMember;
	}
}
