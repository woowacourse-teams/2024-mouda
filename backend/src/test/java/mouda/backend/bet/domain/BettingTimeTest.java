package mouda.backend.bet.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BettingTimeTest {

    @DisplayName("배팅시간은 분까지만 저장한다.")
    @Test
    void create() {
        // given
        LocalDateTime givenTime = LocalDateTime.of(2024, 10, 1, 12, 30, 45, 123456789);

        // when
        BettingTime bettingTime = new BettingTime(givenTime);

        // then
        LocalDateTime expectedTime = LocalDateTime.of(2024, 10, 1, 12, 30, 0, 0);
        assertEquals(expectedTime, bettingTime.getBettingTime());
    }
}
