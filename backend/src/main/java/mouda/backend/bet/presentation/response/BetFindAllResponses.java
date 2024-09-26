package mouda.backend.bet.presentation.response;

import java.util.List;

import mouda.backend.bet.domain.BetDetails;

public record BetFindAllResponses(

) {
	public static BetFindAllResponses toResponse(List<BetDetails> betDetails) {
		return null;
	}
}
