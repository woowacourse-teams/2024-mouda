package mouda.backend.bet.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.business.BetService;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.exception.BetException;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;

@SpringBootTest
class BetWriterTest extends DarakbangSetUp {

	@Autowired
	BetService betService;

	@Autowired
	BetRepository betRepository;

	@Autowired
	BetDarakbangMemberRepository betDarakbangMemberRepository;

	@DisplayName("추첨 시간 이후 참여할 수 없다.")
	@Test
	void failToParticipate_pastBettingTIme() {
		// given
		LocalDateTime pastBettingTime = LocalDateTime.now().minusSeconds(1);
		BetEntity bet = betRepository.save(
			BetEntityFixture.getBetEntity(darakbang.getId(), darakbangAnna.getId(), pastBettingTime));

		// when & then
		assertThatThrownBy(() -> betService.participateBet(darakbang.getId(), bet.getId(), darakbangHogee))
			.isInstanceOf(BetException.class);
	}

	@DisplayName("이미 당첨자가 존재하면 참여할 수 없다.")
	@Test
	void failToParticipate_loserExists() {
		// given
		BetEntity bet = betRepository.save(
			BetEntityFixture.getDrawedBetEntity(darakbang.getId(), darakbangAnna.getId()));

		// when & then
		assertThatThrownBy(() -> betService.participateBet(darakbang.getId(), bet.getId(), darakbangHogee))
			.isInstanceOf(BetException.class);
	}

	@DisplayName("중복 참여할 수 없다.")
	@Test
	void failToParticipate_alreadyParticipate() {
		// given
		BetEntity bet = betRepository.save(
			BetEntityFixture.getFutureBetEntity(darakbang.getId(), darakbangAnna.getId()));
		betDarakbangMemberRepository.save(new BetDarakbangMemberEntity(darakbangAnna, bet));

		// when & then
		assertThatThrownBy(() -> betService.participateBet(darakbang.getId(), bet.getId(), darakbangAnna))
			.isInstanceOf(BetException.class);
	}

	@DisplayName("참여에 성공한다.")
	@Test
	void participate() {
		// given
		BetEntity bet = betRepository.save(
			BetEntityFixture.getFutureBetEntity(darakbang.getId(), darakbangAnna.getId()));

		// when
		betService.participateBet(darakbang.getId(), bet.getId(), darakbangHogee);

		// then
		List<BetDarakbangMemberEntity> participants = betDarakbangMemberRepository.findAllByBetId(bet.getId());
		assertThat(participants).hasSize(1);
	}
}
