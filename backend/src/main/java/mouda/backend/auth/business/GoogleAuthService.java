package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.GoogleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.presentation.request.GoogleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

	private final GoogleOauthManager googleOauthManager;
	private final LoginManager loginManager;

	public LoginResponse oauthLogin(GoogleOauthRequest googleOauthRequest) {
		String memberName = googleOauthManager.getMemberName(googleOauthRequest.idToken());
		String socialLoginId = googleOauthManager.getSocialLoginId(googleOauthRequest.idToken());
		if (googleOauthRequest.memberId() != null) {
			String accessToken = loginManager.updateOauth(googleOauthRequest.memberId(), OauthType.GOOGLE,
				socialLoginId);
			return new LoginResponse(accessToken);
		}
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.GOOGLE, socialLoginId);
		return new LoginResponse(loginProcessResult.accessToken());
	}

	public Member findMember(String token) {
		return null;
	}

	public void checkAuthentication(String token) {

	}
}
