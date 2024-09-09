package mouda.backend.moim.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MoimChat {

	private final Chamyo chamyo;
	private final int currentPeople;
	private final String lastContent;

	@Builder
	public MoimChat(
		Chamyo chamyo,
		int currentPeople,
		String lastContent
	) {
		this.chamyo = chamyo;
		this.currentPeople = currentPeople;
		this.lastContent = lastContent;
	}
}
