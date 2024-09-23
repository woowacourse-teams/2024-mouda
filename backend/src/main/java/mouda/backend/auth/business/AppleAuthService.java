package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.Infrastructure.AppleOauthClient;
import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class AppleAuthService implements AuthService {

	private final AppleOauthClient oauthClient;

	@Override
	public LoginResponse oauthLogin(OauthRequest oauthRequest) {
		String accessToken = oauthClient.getIdToken(oauthRequest.code());
		
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
