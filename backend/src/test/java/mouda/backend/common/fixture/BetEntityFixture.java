package mouda.backend.common.fixture;

import java.time.LocalDateTime;

import mouda.backend.bet.entity.BetEntity;

public class BetEntityFixture {

    public static BetEntity getBetEntity(long darakbangId, long moimerId) {
        return BetEntity.builder()
            .title("testBet")
            .bettingTime(LocalDateTime.now()
                .withSecond(0)
                .withNano(0))
            .darakbangId(darakbangId)
            .moimerId(moimerId)
            .build();
    }

    public static BetEntity getBetEntity(String title, long darakbangId, long moimerId) {
        return BetEntity.builder()
            .title(title)
            .bettingTime(LocalDateTime.now()
                .withSecond(0)
                .withNano(0))
            .darakbangId(darakbangId)
            .moimerId(moimerId)
            .build();
    }

    public static BetEntity getBetEntity(long darakbangId, long moimerId, LocalDateTime bettingTime) {
        return BetEntity.builder()
            .title("테바바보")
            .bettingTime(bettingTime.withSecond(0).withNano(0))
            .darakbangId(darakbangId)
            .moimerId(moimerId)
            .build();
    }

    public static BetEntity getDrawedBetEntity(long darakbangId, long moimerId) {
        return BetEntity.builder()
            .title("테바바보")
            .bettingTime(LocalDateTime.now()
                .withSecond(0)
                .withNano(0))
            .darakbangId(darakbangId)
            .moimerId(moimerId)
            .loserDarakbangMemberId(moimerId)
            .build();
    }
}
