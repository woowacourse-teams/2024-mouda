package mouda.backend.chat.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class BetAttributes implements Attributes {

	private final String title;
	private final Boolean isLoser;
	private final Long betId;
	private final Loser loser;

	public BetAttributes(String title, Boolean isLoser, Long betId, Loser loser) {
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
		attributes.put("loser", Map.of(
			"nickname", loser.getNickname(),
			"profile", loser.getProfile(),
			"role", loser.getRole()
		));
		return attributes;
	}

}
