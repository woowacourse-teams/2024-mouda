package mouda.backend.bet.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BetDetails {

	private final Long id;
	private final String title;
	private final LocalDateTime bettingTime;

	@Builder
	public BetDetails(Long id, String title, LocalDateTime bettingTime) {
		this.id = id;
		this.title = title;
		this.bettingTime = bettingTime;
	}

	public static BetDetails create(String title, int waitingMinutes) {
		LocalDateTime bettingTime = createBettingTime(waitingMinutes);

		return BetDetails.builder()
			.title(title)
			.bettingTime(bettingTime)
			.build();
	}

	private static LocalDateTime createBettingTime(int waitingMinutes) {
		return LocalDateTime.now()
			.plusMinutes(waitingMinutes)
			.withSecond(0)
			.withNano(0);
	}
}
