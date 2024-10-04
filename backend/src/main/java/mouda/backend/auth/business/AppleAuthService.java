package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.AppleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

@Service
@RequiredArgsConstructor
public class AppleAuthService implements AuthService {

	private final AppleOauthManager oauthManager;
	private final LoginManager loginManager;

	@Override
	public LoginResponse oauthLogin(AppleOauthRequest oauthRequest) {
		String socialLoginId = oauthManager.getSocialLoginId(oauthRequest.code());
		if (oauthRequest.memberId() != null) {
			String accessToken = loginManager.updateOauth(oauthRequest.memberId(), OauthType.APPLE, socialLoginId);
			return new LoginResponse(accessToken);
		}
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.APPLE, socialLoginId);
		return new LoginResponse(loginProcessResult.accessToken());
	}

	@Override
	public Member findMember(String token) {
		return null;
	}

	@Override
	public void checkAuthentication(String token) {
	}
}
