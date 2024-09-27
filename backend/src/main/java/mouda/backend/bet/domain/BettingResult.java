package mouda.backend.bet.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class BettingResult {

	private final Map<Bet, Loser> results;

	public BettingResult(List<Bet> bets) {
		this.results = process(bets);
	}

	private static Map<Bet, Loser> process(List<Bet> bets) {
		Map<Bet, Loser> results = new HashMap<>();

		bets.forEach(bet -> {
			results.put(bet, bet.draw());
		});
		return results;
	}
}
