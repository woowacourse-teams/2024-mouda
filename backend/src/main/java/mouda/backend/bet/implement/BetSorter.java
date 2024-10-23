package mouda.backend.bet.implement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import mouda.backend.bet.domain.Bet;

@Component
public class BetSorter {

	public List<Bet> sort(List<Bet> bets) {
		List<Bet> mutableBets = new ArrayList<>(bets);

		mutableBets.sort(Comparator
			.comparing(Bet::hasLoser)
			.thenComparing(Bet::timeDifferenceInMinutesWithNow)
		);

		return mutableBets;
	}
}
