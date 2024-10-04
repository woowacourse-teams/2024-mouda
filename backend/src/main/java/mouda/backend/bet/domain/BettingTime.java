package mouda.backend.bet.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Getter;

@Getter
public class BettingTime {

    private static final int NO_SECONDS = 0;
    private static final int NO_NANOS = 0;

    private final LocalDateTime bettingTime;

    public BettingTime(LocalDateTime bettingTime) {
        this.bettingTime = normalize(bettingTime);
    }

    private LocalDateTime normalize(LocalDateTime bettingTime) {
        return bettingTime.withSecond(NO_SECONDS).withNano(NO_NANOS);
    }

    public long timeDifferenceInMinutesWithNow() {
        return Math.abs(ChronoUnit.MINUTES.between(normalize(LocalDateTime.now()), this.bettingTime));
    }
}
