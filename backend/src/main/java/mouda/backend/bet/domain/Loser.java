package mouda.backend.bet.domain;

import lombok.Getter;

@Getter
public class Loser {

	private final long id;
	private final String name;

	public Loser(long id, String name) {
		this.id = id;
		this.name = name;
	}
}
