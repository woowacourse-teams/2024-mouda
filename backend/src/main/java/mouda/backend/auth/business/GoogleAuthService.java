package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.GoogleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.presentation.request.GoogleOauthReqeust;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

	private final GoogleOauthManager googleOauthManager;
	private final LoginManager loginManager;

	public LoginResponse oauthLogin(GoogleOauthReqeust googleOauthReqeust) {
		String memberName = googleOauthManager.getMemberName(googleOauthReqeust.idToken());
		String socialLoginId = googleOauthManager.getSocialLoginId(googleOauthReqeust.idToken());
		String accessToken = loginManager.processSocialLogin(OauthType.GOOGLE, socialLoginId);
		return new LoginResponse(accessToken);
	}

	public Member findMember(String token) {
		return null;
	}

	public void checkAuthentication(String token) {

	}
}
