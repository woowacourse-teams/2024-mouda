package mouda.backend.bet.presentation.response;

import java.time.LocalDateTime;

import mouda.backend.bet.domain.Bet;

public record BetFindAllResponse(
	long id,
	String title,
	int currentParticipants,
	LocalDateTime deadline,
	boolean isAnnounced
) {
	public static BetFindAllResponse from(Bet bet) {
		return new BetFindAllResponse(
			bet.getBetDetails().getId(),
			bet.getBetDetails().getTitle(),
			bet.getParticipants().size(),
			bet.getBetDetails().getBettingTime(),
			bet.hasLoser()
		);
	}
}
