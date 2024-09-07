package mouda.backend.common.fixture;

import mouda.backend.member.domain.Member;

public class MemberFixture {

	public static Member getHogee() {
		return Member.builder()
			.nickname("hogee")
			.build();
	}

	public static Member getAnna() {
		return Member.builder()
			.nickname("anna")
			.build();
	}

	public static Member getTebah() {
		return Member.builder()
			.nickname("tebah")
			.build();
	}
}
