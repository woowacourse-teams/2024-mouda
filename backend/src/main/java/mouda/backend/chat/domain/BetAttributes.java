package mouda.backend.chat.domain;

import java.util.Map;

import lombok.Getter;

@Getter
public class BetAttributes implements Attributes {

	private Boolean isLoser;
	private Long betId;
	private Loser loser;

	public BetAttributes(Boolean isLoser, Long betId, Loser loser) {
		this.isLoser = isLoser;
		this.betId = betId;
		this.loser = loser;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}
}
