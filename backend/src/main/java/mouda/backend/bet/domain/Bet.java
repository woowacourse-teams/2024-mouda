package mouda.backend.bet.domain;

import java.util.List;
import java.util.Random;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Bet {

	private final BetDetails betDetails;
	private final List<Participant> participants;

	@Builder
	public Bet(BetDetails betDetails, List<Participant> participants) {
		this.betDetails = betDetails;
		this.participants = participants;
	}

	public Loser draw() {
		Random random = new Random();
		int loserNumber = random.nextInt(participants.size());
		return participants.get(loserNumber).toLoser();
	}

	public long getId() {
		return betDetails.getId();
	}
}
