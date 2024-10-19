package mouda.backend.auth.business;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.KakaoOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private final AccessTokenProvider accessTokenProvider;
	private final KakaoOauthManager oauthManager;
	private final LoginManager loginManager;
	private final MemberWriter memberWriter;
	private final MemberFinder memberFinder;

	public LoginResponse oauthLogin(OauthRequest oauthRequest) {
		String kakaoId = oauthManager.getSocialLoginId(oauthRequest.code());
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.KAKAO, kakaoId, "name");

		return new LoginResponse(loginProcessResult.accessToken());
	}

	public LoginResponse basicLoginAnna() {
		Member member = memberFinder.findBySocialId("testSocialLoginId");
		return new LoginResponse(accessTokenProvider.provide(member));
	}

	public LoginResponse basicLoginHogee() {
		Member member = Member.builder()
			.name("조호연")
			.loginDetail(new LoginDetail(OauthType.GOOGLE, UUID.randomUUID().toString()))
			.build();
		memberWriter.append(member);
		return new LoginResponse(accessTokenProvider.provide(member));
	}
}
