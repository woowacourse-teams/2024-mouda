package mouda.backend.bet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bet_darakbang_member")
public class BetDarakbangMemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private DarakbangMember darakbangMember;

	@JoinColumn(nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private BetEntity bet;

	public BetDarakbangMemberEntity(DarakbangMember darakbangMember, BetEntity bet) {
		this.darakbangMember = darakbangMember;
		this.bet = bet;
	}
}
