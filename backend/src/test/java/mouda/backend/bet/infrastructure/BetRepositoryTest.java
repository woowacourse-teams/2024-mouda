package mouda.backend.bet.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetEntity;
import mouda.backend.common.fixture.BetEntityFixture;

@SpringBootTest
class BetRepositoryTest {

	@Autowired
	BetRepository betRepository;

	@DisplayName("현재 시각과 동일하고 추첨하지 않은 배팅 목록을 가져온다.")
	@Test
	void findAllByBettingTimeAndLoserDarakbangMemberIdIsNull() {
		// given
		BetEntity betEntity1 = BetEntityFixture.getBetEntity(1L, 1L);
		BetEntity betEntity2 = BetEntityFixture.getDrawedBetEntity(1L, 2L);

		betRepository.save(betEntity1);
		betRepository.save(betEntity2);

		// when
		List<BetEntity> betEntities = betRepository.findAllByBettingTimeAndLoserDarakbangMemberIdIsNull(LocalDateTime.now().withSecond(0).withNano(0));

		//then
		assertThat(betEntities).hasSize(1);
	}
}
