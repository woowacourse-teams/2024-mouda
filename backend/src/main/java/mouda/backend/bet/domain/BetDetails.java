package mouda.backend.bet.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BetDetails {

	private final long id;
	private final String title;
	private final BettingTime bettingTime;
	private final long moimerId;
	private final Long loserId;

	@Builder
	public BetDetails(long id, String title, LocalDateTime bettingTime, long moimerId, Long loserId) {
		this.id = id;
		this.title = title;
		this.bettingTime = new BettingTime(bettingTime);
		this.moimerId = moimerId;
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

	public boolean pastBettingTime() {
		return bettingTime.isPast();
	}
}
