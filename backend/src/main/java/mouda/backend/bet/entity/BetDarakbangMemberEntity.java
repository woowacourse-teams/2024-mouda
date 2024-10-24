package mouda.backend.bet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.bet.domain.BetRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Entity
@Getter
@NoArgsConstructor
@Table(
	name = "bet_darakbang_member",
	uniqueConstraints = {
		@UniqueConstraint(
			columnNames = {"bet_id", "darakbang_member_id"}
		)
	}
)
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

	private long lastReadChatId;

	public BetDarakbangMemberEntity(DarakbangMember darakbangMember, BetEntity bet) {
		this.darakbangMember = darakbangMember;
		this.bet = bet;
	}

	public void updateLastChat(Long lastReadChatId) {
		this.lastReadChatId = lastReadChatId;
	}

	public String getRole(long moimerId) {
		if (darakbangMember.getId() == moimerId) {
			return BetRole.MOIMER.name();
		}
		return BetRole.MOIMEE.name();
	}
}
