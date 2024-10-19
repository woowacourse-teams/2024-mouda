package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.implement.GoogleOauthManager;
import mouda.backend.auth.implement.LoginManager;
import mouda.backend.auth.presentation.request.GoogleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.OauthType;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

	private final GoogleOauthManager googleOauthManager;
	private final LoginManager loginManager;

	public LoginResponse oauthLogin(GoogleOauthRequest googleOauthRequest) {
		String name = googleOauthManager.getMemberName(googleOauthRequest.idToken());
		String identifier = googleOauthManager.getIdentifier(googleOauthRequest.idToken());
		return processGoogleLogin(identifier, name);
	}

	private LoginResponse processGoogleLogin(String identifier, String name) {
		LoginProcessResult loginProcessResult = loginManager.processSocialLogin(OauthType.GOOGLE, identifier, name);
		return new LoginResponse(loginProcessResult.accessToken());
	}
}
