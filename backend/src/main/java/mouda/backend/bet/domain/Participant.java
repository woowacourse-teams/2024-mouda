package mouda.backend.bet.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Participant {

	private final long id;
	private final String name;
	private final String profile;

	@Builder
	public Participant(long id, String name, String profile) {
		this.id = id;
		this.name = name;
		this.profile = profile;
	}

	public Loser toLoser() {
		return new Loser(id, name);
	}
}
