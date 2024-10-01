package mouda.backend.bet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bet")
public class BetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private LocalDateTime bettingTime;

	private Long loserDarakbangMemberId;

	@Column(nullable = false, updatable = false)
	private long darakbangId;

	@Column(nullable = false)
	private long moimerId;

	@Builder
	private BetEntity(Long id, String title, LocalDateTime bettingTime, Long loserDarakbangMemberId, long darakbangId, long moimerId) {
		this.id = id;
		this.title = title;
		this.bettingTime = bettingTime;
		this.loserDarakbangMemberId = loserDarakbangMemberId;
		this.darakbangId = darakbangId;
		this.moimerId = moimerId;
	}

	public static BetEntity from(Bet bet) {
		BetDetails betDetails = bet.getBetDetails();

		return BetEntity.builder()
			.id(bet.getId())
			.title(betDetails.getTitle())
			.bettingTime(betDetails.getBettingTime())
			.loserDarakbangMemberId(bet.getLoserId())
			.moimerId(bet.getMoimerId())
			.build();
	}

	public static BetEntity create(Bet bet, long darakbangId) {
		return BetEntity.builder()
			.title(bet.getBetDetails().getTitle())
			.bettingTime(bet.getBetDetails().getBettingTime())
			.moimerId(bet.getMoimerId())
			.darakbangId(darakbangId)
			.build();
	}

	public BetDetails toBetDetails() {
		return BetDetails.builder()
			.id(id)
			.title(title)
			.bettingTime(bettingTime)
			.build();
	}
}
