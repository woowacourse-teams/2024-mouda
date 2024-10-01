package mouda.backend.bet.implement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import mouda.backend.bet.domain.Bet;

@Component
public class BetSorter {

    private static final int HAS_LOSER = 1;
    private static final int NO_LOSER = 0;

    public List<Bet> sort(List<Bet> bets) {
        List<Bet> mutableBets = new ArrayList<>(bets);

        mutableBets.sort(Comparator
            .comparing(Bet::timeDifferenceInMinutesWithNow)
            .thenComparing(bet -> bet.hasLoser() ? HAS_LOSER : NO_LOSER)
        );

        return mutableBets;
    }
}
