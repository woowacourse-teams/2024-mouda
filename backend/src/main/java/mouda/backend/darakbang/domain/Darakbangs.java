package mouda.backend.darakbang.domain;

import java.util.List;

public class Darakbangs {

	private final List<Darakbang> darakbangs;

	public Darakbangs(List<Darakbang> darakbangs) {
		this.darakbangs = darakbangs;
	}

	public List<Darakbang> getDarakbangs() {
		return darakbangs;
	}
}
