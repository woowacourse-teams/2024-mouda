package mouda.backend.fixture;

import mouda.backend.darakbang.domain.Darakbang;

public class DarakbangFixture {

	public static Darakbang getDarakbangWithWooteco() {
		return Darakbang.builder()
			.name("우아한테크코스")
			.code("SOFABABO1")
			.build();
	}
}
