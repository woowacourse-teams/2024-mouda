package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.KakaoOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.KakaoLoginResponse;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberWriter;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

	private final AccessTokenProvider accessTokenProvider;
	private final KakaoOauthManager oauthManager;
	private final LoginManager loginManager;
	private final MemberWriter memberWriter;

	public KakaoLoginResponse oauthLogin(OauthRequest oauthRequest) {
		String kakaoId = oauthManager.getSocialLoginId(oauthRequest.code());
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.KAKAO, kakaoId, "name");

		return new KakaoLoginResponse(loginProcessResult.memberId(), loginProcessResult.accessToken());
	}

	public LoginResponse basicLogin() {
		Member member = Member.builder()
			.name("김민겸")
			.loginDetail(new LoginDetail(OauthType.GOOGLE, "google-social-login-id"))
			.build();
		memberWriter.append(member);
		return new LoginResponse(accessTokenProvider.provide(member));
	}
}
