package mouda.backend.bet.presentation.response;

import mouda.backend.bet.domain.Loser;

public record BetResultResponse(
	String nickname
) {
	public static BetResultResponse from(Loser loser) {
		return new BetResultResponse(loser.getName());
	}
}
