package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.AppleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.OauthType;

@Service
@RequiredArgsConstructor
public class AppleAuthService {

	private final AppleOauthManager oauthManager;
	private final LoginManager loginManager;

	public LoginResponse oauthLogin(AppleOauthRequest oauthRequest) {
		String socialLoginId = oauthManager.getSocialLoginId(oauthRequest.code());
		if (oauthRequest.memberId() != null) {
			String accessToken = loginManager.updateOauth(oauthRequest.memberId(), OauthType.APPLE, socialLoginId);
			return new LoginResponse(accessToken);
		}
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(
			OauthType.APPLE, socialLoginId, oauthRequest.name());
		return new LoginResponse(loginProcessResult.accessToken());
	}
}
