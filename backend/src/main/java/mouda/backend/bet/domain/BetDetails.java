package mouda.backend.bet.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BetDetails {

	private final Long id;
	private final String title;
	private final BettingTime bettingTime;
	private final Long loserId;

	@Builder
	public BetDetails(Long id, String title, LocalDateTime bettingTime, Long loserId) {
		this.id = id;
		this.title = title;
		this.bettingTime = new BettingTime(bettingTime);
		this.loserId = loserId;
	}

	public static BetDetails create(String title, int waitingMinutes) {
		LocalDateTime bettingTime = LocalDateTime.now().plusMinutes(waitingMinutes);

		return BetDetails.builder()
			.title(title)
			.bettingTime(bettingTime)
			.build();
	}

	public LocalDateTime getBettingTime() {
		return bettingTime.getBettingTime();
	}

	public long timeDifferenceInMinutesWithNow() {
		return bettingTime.timeDifferenceInMinutesWithNow();
	}

	public boolean hasLoser() {
		return loserId != null;
	}
}
