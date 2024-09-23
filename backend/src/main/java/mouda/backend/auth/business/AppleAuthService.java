package mouda.backend.auth.business;

import org.springframework.stereotype.Service;

import mouda.backend.auth.presentation.request.OauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;

@Service
public class AppleAuthService implements AuthService {

	@Override
	public LoginResponse oauthLogin(OauthRequest oauthRequest) {
		return null;
	}

	@Override
	public Member findMember(String token) {
		return null;
	}

	@Override
	public void checkAuthentication(String token) {

	}
}
