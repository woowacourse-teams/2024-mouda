package mouda.backend.common.fixture;

import mouda.backend.darakbang.domain.Darakbang;

public class DarakbangFixture {

	public static Darakbang getDarakbangWithWooteco() {
		return Darakbang.builder()
			.name("우아한테크코스")
			.code("SOFABAC")
			.build();
	}

	public static Darakbang getDarakbangWithMouda() {
		return Darakbang.builder()
			.name("모우다")
			.code("SOFABAB")
			.build();
	}
}
