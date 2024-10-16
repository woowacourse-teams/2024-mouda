package mouda.backend.chat.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class BetAttributes implements Attributes {

	private final String title;
	private final boolean isLoser;
	private final long betId;
	private final Participant loser;

	public BetAttributes(String title, Boolean isLoser, Long betId, Participant loser) {
		this.title = title;
		this.isLoser = isLoser;
		this.betId = betId;
		this.loser = loser;
	}

	@Override
	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("title", title);
		attributes.put("isLoser", isLoser);
		attributes.put("betId", betId);
		attributes.put("loser", loser);
		return attributes;
	}
}
