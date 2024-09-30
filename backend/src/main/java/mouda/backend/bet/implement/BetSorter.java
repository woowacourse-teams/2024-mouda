package mouda.backend.bet.implement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import mouda.backend.bet.domain.Bet;

@Component
public class BetSorter {

    public List<Bet> sort(List<Bet> bets) {
        List<Bet> mutableBets = new ArrayList<>(bets);
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        mutableBets.sort(Comparator
            .comparing((Bet bet) -> Math.abs(ChronoUnit.MINUTES.between(now, bet.getBetDetails().getBettingTime())))
            .thenComparing(bet -> bet.hasLoser() ? 0 : 1)
        );
        return mutableBets;
    }
}
