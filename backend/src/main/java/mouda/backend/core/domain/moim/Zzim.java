package mouda.backend.core.domain.moim;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;

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
	private DarakbangMember darakbangMember;

	@Builder
	public Zzim(Moim moim, DarakbangMember darakbangMember) {
		this.moim = moim;
		this.darakbangMember = darakbangMember;
	}
}
