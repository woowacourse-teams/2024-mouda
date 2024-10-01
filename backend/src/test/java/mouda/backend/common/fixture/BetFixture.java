package mouda.backend.common.fixture;

import java.time.LocalDateTime;
import java.util.Arrays;

import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.domain.Participant;

public class BetFixture {

    public static Bet createBet(Long id, LocalDateTime bettingTime, Long loserId) {
        BetDetails betDetails = BetDetails.builder()
            .id(id)
            .title("Bet " + id)
            .bettingTime(bettingTime)
            .build();

        return Bet.builder()
            .betDetails(betDetails)
            .participants(Arrays.asList(new Participant(1L, "테바"), new Participant(2L, "테니")))
            .moimerId(1L)
            .loserId(loserId)
            .build();
    }
}
