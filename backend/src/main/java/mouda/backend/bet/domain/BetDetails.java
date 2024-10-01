package mouda.backend.bet.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BetDetails {

    private final Long id;
    private final String title;
    private final BettingTime bettingTime;

    @Builder
    public BetDetails(Long id, String title, LocalDateTime bettingTime) {
        this.id = id;
        this.title = title;
        this.bettingTime = new BettingTime(bettingTime);
    }

    public static BetDetails create(String title, int waitingMinutes) {
        LocalDateTime bettingTime = LocalDateTime.now().plusMinutes(waitingMinutes);

        return BetDetails.builder()
            .title(title)
            .bettingTime(bettingTime)
            .build();
    }

    public LocalDateTime getBettingTime() {
        return bettingTime.getBettingTime();
    }

    public long timeDifferenceInMinutesWithNow() {
        return bettingTime.timeDifferenceInMinutesWithNow();
    }
}
