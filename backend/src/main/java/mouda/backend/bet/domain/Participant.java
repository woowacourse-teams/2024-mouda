package mouda.backend.bet.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Participant {

	private final long id;
	private final String name;

	@Builder
	public Participant(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Loser toLoser() {
		return new Loser(id, name);
	}
}
