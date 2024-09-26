package mouda.backend.bet.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.domain.Loser;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bet")
public class BetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private LocalDateTime bettingTime;

	private Long loserDarakbangMemberId;

	@Builder
	public BetEntity(String title, LocalDateTime bettingTime, Long loserDarakbangMemberId) {
		this.title = title;
		this.bettingTime = bettingTime;
		this.loserDarakbangMemberId = loserDarakbangMemberId;
	}

	public static BetEntity of(Bet bet, Loser loser) {
		BetDetails betDetails = bet.getBetDetails();
		return new BetEntity(
			bet.getId(),
			betDetails.getTitle(),
			betDetails.getBettingTime(),
			loser.getId()
		);
	}

	public static BetEntity create(BetDetails betDetails) {
		return BetEntity.builder()
			.title(betDetails.getTitle())
			.bettingTime(betDetails.getBettingTime())
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
