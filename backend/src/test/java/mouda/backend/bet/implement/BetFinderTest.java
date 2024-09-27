package mouda.backend.bet.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;

class BetFinderTest extends DarakbangSetUp {

	@Autowired
	BetFinder betFinder;

	@Autowired
	BetRepository betRepository;

	@DisplayName("다락방에 존재하는 안내면진다를 조회한다.")
	@Test
	void find() {
		// given
		long darakbangId = darakbang.getId();
		String title = "테니바보";
		BetEntity betEntity = BetEntityFixture.getBetEntity(title, darakbangId, darakbangAnna.getId());
		BetEntity savedBetEntity = betRepository.save(betEntity);

		// when
		Bet bet = betFinder.find(darakbangId, savedBetEntity.getId());

		//then
		assertThat(bet.getId()).isEqualTo(savedBetEntity.getId());
		assertThat(bet.getBetDetails().getTitle()).isEqualTo(title);
	}

	@DisplayName("다락방에 존재하는 모든 안내면진다를 조회한다.")
	@Test
	void findAllDetails() {
		// given
		long darakbangId = darakbang.getId();
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId());
		betRepository.save(betEntity);

		// when
		List<Bet> betDetails = betFinder.findAllByDarakbangId(darakbangId);
		List<Bet> emptyBetDetails = betFinder.findAllByDarakbangId(123L);

		//then
		assertThat(betDetails).hasSize(1);
		assertThat(emptyBetDetails).hasSize(0);
	}

	@DisplayName("추첨 가능한 모든 안내면진다를 조회한다.")
	@Test
	void findAllDrawableBets() {
		// given
		long darakbangId = darakbang.getId();
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId());
		betRepository.save(betEntity);

		long moudaDarakbangId = mouda.getId();
		BetEntity betEntity2 = BetEntityFixture.getBetEntity(moudaDarakbangId, darakbangAnna.getId());
		betRepository.save(betEntity2);

		BetEntity betEntity3 = BetEntityFixture.getBetEntity(moudaDarakbangId, darakbangAnna.getId(), LocalDateTime.now().plusMinutes(10));
		betRepository.save(betEntity3);

		// when
		List<Bet> bets = betFinder.findAllDrawableBet();

		//then
		assertThat(bets).hasSize(2);
	}
}
