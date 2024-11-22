package mouda.backend.moim.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MoimCurrentPeople {
	private final long moimId;
	private final long currentPeople;
}
