package mouda.backend.please.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PleaseWithInterest implements Comparable<PleaseWithInterest> {

	private final Please please;
	private final boolean isInterested;
	private final long interestCount;

	@Override
	public int compareTo(PleaseWithInterest other) {
		int interestCompare = Long.compare(other.interestCount, this.interestCount);

		if (interestCompare != 0) {
			return interestCompare;
		}
		return Long.compare(this.please.getId(), other.please.getId());
	}
}
