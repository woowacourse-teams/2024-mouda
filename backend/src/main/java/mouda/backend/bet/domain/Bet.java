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
	private Long loserId;

	@Builder
	public Bet(BetDetails betDetails, List<Participant> participants, long moimerId, long loserId) {
		this.betDetails = betDetails;
		this.participants = participants;
		this.moimerId = moimerId;
		this.loserId = loserId;
	}

	public void draw() {
		Random random = new Random();
		this.loserId = random.nextLong(participants.size());
	}

	public long getId() {
		return betDetails.getId();
	}

	public boolean hasLoser() {
		return loserId != null;
	}

	public long getLoserId() {
		if (loserId == null) {
			throw new IllegalArgumentException("당첨자가 존재하지 않습니다.");
		}
		return loserId;
	}
}
