package mouda.backend.bet.domain;

import java.util.List;
import java.util.Random;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Bet {

	private final BetDetails betDetails;
	private final List<Participant> participants;
	private final long moimerId;

	@Builder
	public Bet(BetDetails betDetails, List<Participant> participants, long moimerId) {
		this.betDetails = betDetails;
		this.participants = participants;
		this.moimerId = moimerId;
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
