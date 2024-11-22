package mouda.backend.bet.presentation.response;

import java.util.List;

import mouda.backend.bet.domain.Bet;

public record BetFindAllResponses(
	List<BetFindAllResponse> bets
) {
	public static BetFindAllResponses toResponse(List<Bet> bets) {
		List<BetFindAllResponse> responses = bets.stream()
			.map(BetFindAllResponse::from)
			.toList();
		return new BetFindAllResponses(responses);
	}
}
