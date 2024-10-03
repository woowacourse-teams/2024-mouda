package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.GoogleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.presentation.response.LegacyOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

@Service
@RequiredArgsConstructor
public class GoogleAuthService implements AuthService {

	private final GoogleOauthManager googleOauthManager;
	private final LoginManager loginManager;

	@Override
	public LoginResponse oauthLogin(LegacyOauthRequest oauthRequest) {
		String memberName = googleOauthManager.getMemberName(oauthRequest.code());
		String socialLoginId = googleOauthManager.getSocialLoginId(oauthRequest.code());
		if (oauthRequest.memberId() != null) {
			String accessToken = loginManager.updateOauth(oauthRequest.memberId(), OauthType.GOOGLE, socialLoginId);
			return new LoginResponse(accessToken);
		}
		String accessToken = loginManager.processSocialLogin(OauthType.GOOGLE, socialLoginId);
		return new LoginResponse(accessToken);
	}

	@Override
	public Member findMember(String token) {
		return null;
	}

	@Override
	public void checkAuthentication(String token) {

	}
}
