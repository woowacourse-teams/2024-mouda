package mouda.backend.bet.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;

class BetFinderTest extends DarakbangSetUp {

	@Autowired
	BetFinder betFinder;

	@Autowired
	BetRepository betRepository;

	@DisplayName("다락방에 존재하는 모든 안내면진다를 조회한다.")
	@Test
	void findAllDetails() {
		// given
		long darakbangId = darakbang.getId();
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbangId, darakbangAnna.getId());
		betRepository.save(betEntity);

		// when
		List<BetDetails> betDetails = betFinder.findAllDetails(darakbangId);
		List<BetDetails> emptyBetDetails = betFinder.findAllDetails(123L);

		//then
		assertThat(betDetails).hasSize(1);
		assertThat(emptyBetDetails).hasSize(0);
	}
}
