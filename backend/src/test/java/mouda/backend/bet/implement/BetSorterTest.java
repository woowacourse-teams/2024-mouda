package mouda.backend.bet.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.bet.domain.Bet;
import mouda.backend.common.fixture.BetFixture;

public class BetSorterTest {

	private BetSorter betSorter;

	@BeforeEach
	public void setUp() {
		betSorter = new BetSorter();
	}

	@DisplayName("현재 시간과의 차이가 적은 순서대로 정렬한다.")
	@Test
	public void sortByTimeDifference() {
		// given
		LocalDateTime now = LocalDateTime.now();
		Bet bet1 = BetFixture.createBet(1L, now.plusMinutes(5), null);
		Bet bet2 = BetFixture.createBet(2L, now.plusMinutes(1), null);
		Bet bet3 = BetFixture.createBet(3L, now.plusMinutes(10), null);
		Bet bet4 = BetFixture.createBet(4L, now.minusMinutes(3), null);

		// when
		List<Bet> bets = Arrays.asList(bet1, bet2, bet3, bet4);
		List<Bet> sortedBets = betSorter.sort(bets);

		// then
		assertThat(sortedBets).containsExactly(bet2, bet4, bet1, bet3);
	}

	@DisplayName("모든 베팅이 추첨되었다면 추첨시간과 가까운 순으로 배치한다.")
	@Test
	public void sortByLoserIdWhenTimeIsEqual() {
		// given
		LocalDateTime now = LocalDateTime.now();
		Bet bet1 = BetFixture.createBet(1L, now.plusMinutes(5), 100L);
		Bet bet2 = BetFixture.createBet(2L, now.plusMinutes(1), 100L);
		Bet bet3 = BetFixture.createBet(3L, now.plusMinutes(3), 100L);

		// when
		List<Bet> bets = Arrays.asList(bet1, bet2, bet3);
		List<Bet> sortedBets = betSorter.sort(bets);

		// then
		assertThat(sortedBets).containsExactly(bet2, bet3, bet1);
	}

	@DisplayName("추첨이 완료되지 않은 배팅이 추첨시간과 가까운 순으로 먼저 배치된다. 추첨된 베팅은 추첨시간과 가까운 순으로 뒤에 배치된다.")
	@Test
	public void sortByHasLoser() {
		// given
		LocalDateTime now = LocalDateTime.now();
		Bet bet1 = BetFixture.createBet(1L, now.plusMinutes(5), null);
		Bet bet2 = BetFixture.createBet(2L, now.plusMinutes(1), 100L);
		Bet bet3 = BetFixture.createBet(3L, now.plusMinutes(1), null);
		Bet bet4 = BetFixture.createBet(4L, now.plusMinutes(5), 100L);

		// when
		List<Bet> bets = Arrays.asList(bet1, bet2, bet3, bet4);
		List<Bet> sortedBets = betSorter.sort(bets);

		// then
		assertThat(sortedBets).containsExactly(bet3, bet1, bet2, bet4);
	}
}
