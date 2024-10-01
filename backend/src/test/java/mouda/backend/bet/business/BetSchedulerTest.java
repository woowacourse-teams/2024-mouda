package mouda.backend.bet.business;

import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;
import static org.awaitility.Awaitility.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.common.fixture.DarakbangSetUp;

@SpringBootTest
class BetSchedulerTest extends DarakbangSetUp {

	@Autowired
	BetScheduler betScheduler;

	@Autowired
	BetRepository betRepository;

	@Autowired
	BetDarakbangMemberRepository betDarakbangMemberRepository;

	@DisplayName("스케줄러에 의해 추첨을 실행한다.")
	@Test
	void performScheduledTask() {
		// given
		BetEntity betEntity = BetEntity.builder()
			.title("testBet")
			.bettingTime(LocalDateTime.now()
				.withSecond(0)
				.withNano(0))
			.darakbangId(1L)
			.moimerId(1L)
			.build();

		betRepository.save(betEntity);

		betDarakbangMemberRepository.save(new BetDarakbangMemberEntity(darakbangHogee, betEntity));
		betDarakbangMemberRepository.save(new BetDarakbangMemberEntity(darakbangAnna, betEntity));

		// when & then
		await()
			.atMost(1, MINUTES)
			.untilAsserted(() -> assertThat(hasLoser()).isTrue());

		Optional<BetEntity> savedBet = betRepository.findById(1L);
		assertThat(savedBet).isPresent();
		assertThat(savedBet.get().getLoserDarakbangMemberId()).isNotNull();
		assertThat(savedBet.get().getDarakbangId()).isEqualTo(1L);
	}

	private boolean hasLoser() {
		Optional<BetEntity> savedBetEntity = betRepository.findById(1L);
		return savedBetEntity.isPresent() && savedBetEntity.get().getLoserDarakbangMemberId() != null;
	}
}
