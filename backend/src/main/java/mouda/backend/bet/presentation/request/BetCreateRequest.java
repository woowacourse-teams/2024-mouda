package mouda.backend.bet.presentation.request;

import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;

public record BetCreateRequest(
	String title,
	int waitingMinutes
) {
	public Bet toBet(long moimerId) {
		BetDetails betDetails = BetDetails.create(
			title,
			waitingMinutes
		);
		return Bet.builder()
			.betDetails(betDetails)
			.moimerId(moimerId)
			.build();
	}
}
