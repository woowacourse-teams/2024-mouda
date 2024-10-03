package mouda.backend.common.fixture;

import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

public class MemberFixture {

	public static Member getHogee() {
		return Member.builder()
			.nickname("hogee")
			.loginDetail(new LoginDetail(OauthType.KAKAO, "1234"))
			.build();
	}

	public static Member getAnna() {
		return Member.builder()
			.nickname("anna")
			.loginDetail(new LoginDetail(OauthType.KAKAO, "1234"))
			.build();
	}

	public static Member getAnna(String socialLoginId) {
		return Member.builder()
			.nickname("anna")
			.loginDetail(new LoginDetail(OauthType.KAKAO, socialLoginId))
			.build();
	}

	public static Member getTebah() {
		return Member.builder()
			.nickname("tebah")
			.loginDetail(new LoginDetail(OauthType.KAKAO, "123"))
			.build();
	}
}
