package mouda.backend.bet.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BetTest {

	@DisplayName("당첨자를 결정한다.")
	@Test
	void draw() {
		// given
		List<Participant> participants = List.of(new Participant(1L, "테바"), new Participant(2L, "테니"));
		BetDetails betDetails = BetDetails.builder()
			.id(1L)
			.title("테바 미안")
			.bettingTime(LocalDateTime.now().plusMinutes(5L).withSecond(0).withNano(0))
			.build();

		Bet bet = Bet.builder()
			.betDetails(betDetails)
			.participants(participants)
			.build();

		// when
		bet.draw();

		//then
		assertThat(bet.hasLoser()).isTrue();
	}

	@DisplayName("모이머의 id를 반환한다.")
	@Test
	void getMoimerId() {
		List<Participant> participants = List.of(new Participant(1L, "테바"), new Participant(2L, "테니"));
		BetDetails betDetails = BetDetails.builder()
			.id(1L)
			.title("테바 미안")
			.bettingTime(LocalDateTime.now().plusMinutes(5L).withSecond(0).withNano(0))
			.build();

		long expected = 1L;
		Bet bet = Bet.builder()
			.betDetails(betDetails)
			.participants(participants)
			.moimerId(expected)
			.build();

		// when
		long actual = bet.getMoimerId();

		//then
		assertThat(expected).isEqualTo(actual);
	}
}
