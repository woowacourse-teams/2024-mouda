package mouda.backend.bet.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.bet.implement.BetWriter;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.implement.ChatRoomWriter;

@Service
@RequiredArgsConstructor
public class BetScheduler {

	@Value("${bet.schedule}")
	private String rate;

	private final BetFinder betFinder;
	private final BetWriter betWriter;
	private final ChatRoomWriter chatRoomWriter;

	@Scheduled(cron = "${bet.schedule}")
	public void performScheduledTask() {
		List<Bet> bets = betFinder.findAllDrawableBet();

		bets.forEach(Bet::draw);

		betWriter.saveAll(bets);

		bets.forEach(bet -> chatRoomWriter.append(bet.getId(), bet.getDarakbangId(), ChatRoomType.BET));
	}
}
