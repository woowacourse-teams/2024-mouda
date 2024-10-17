package mouda.backend.bet.domain;

import java.util.List;
import java.util.Random;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Bet {

	private final BetDetails betDetails;
	private final long darakbangId;
	private final List<Participant> participants;
	private final long moimerId;
	private Long loserId;

	@Builder
	public Bet(BetDetails betDetails, long darakbangId, List<Participant> participants, long moimerId, Long loserId) {
		this.betDetails = betDetails;
		this.darakbangId = darakbangId;
		this.participants = participants;
		this.moimerId = moimerId;
		this.loserId = loserId;
	}

	public void draw() {
		Random random = new Random();
		int loserIndex = random.nextInt(participants.size());
		this.loserId = participants.get(loserIndex).getId();
	}

	public boolean hasLoser() {
		return loserId != null;
	}

	public BetRole getMyRole(Long id) {
		if (moimerId == id) {
			return BetRole.MOIMER;
		}
		if (isParticipated(id)) {
			return BetRole.MOIMEE;
		}
		return BetRole.NON_MOIMEE;
	}

	private boolean isParticipated(Long id) {
		return participants.stream()
			.anyMatch(participant -> participant.getId() == id);
	}

	public boolean isLoser(long otherId) {
		return loserId == otherId;
	}

	public long getId() {
		return betDetails.getId();
	}

	public String getTitle() {
		return betDetails.getTitle();
	}

	public long timeDifferenceInMinutesWithNow() {
		return betDetails.timeDifferenceInMinutesWithNow();
	}

	public boolean canNotParticipate() {
		return hasLoser() || betDetails.pastBettingTime();
	}
}
