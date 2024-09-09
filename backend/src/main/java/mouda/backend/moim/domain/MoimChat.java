package mouda.backend.moim.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MoimChat {

	Chamyo chamyo;
	int currentPeople;
	String lastContent;

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
