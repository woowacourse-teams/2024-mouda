package mouda.backend.moim.domain;

import lombok.Getter;

@Getter
public class MoimOverview {

	private final Moim moim;
	private final int currentPeople;
	private final boolean isZzimed;

	public MoimOverview(Moim moim, int currentPeople, boolean isZzimed) {
		this.moim = moim;
		this.currentPeople = currentPeople;
		this.isZzimed = isZzimed;
	}
}
