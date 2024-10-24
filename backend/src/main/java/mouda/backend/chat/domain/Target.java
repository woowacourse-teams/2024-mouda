package mouda.backend.chat.domain;

import lombok.Getter;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.moim.domain.Moim;

@Getter
public class Target {

	private final long targetId;
	private final String title;
	private final boolean isStarted;

	public Target(Moim moim) {
		this.targetId = moim.getId();
		this.title = moim.getTitle();
		this.isStarted = moim.isPastMoim();
	}

	public Target(BetDetails bet) {
		this.targetId = bet.getId();
		this.title = bet.getTitle();
		this.isStarted = true;
	}
}
