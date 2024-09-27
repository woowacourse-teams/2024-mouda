package mouda.backend.bet.domain;

public class Participant {

	private final long id;
	private final String name;

	public Participant(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Loser toLoser() {
		return new Loser(id, name);
	}
}
