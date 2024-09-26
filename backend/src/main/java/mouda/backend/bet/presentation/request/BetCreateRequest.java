package mouda.backend.bet.presentation.request;

import mouda.backend.bet.domain.BetDetails;

public record BetCreateRequest(
	String title,
	int waitingMinutes
) {
	public BetDetails toBetDetails() {
		return BetDetails.create(
			title,
			waitingMinutes
		);
	}
}
