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
}
