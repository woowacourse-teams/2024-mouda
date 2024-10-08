package mouda.backend.auth.business;

import mouda.backend.auth.presentation.request.AppleOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;

public interface AuthService {

	LoginResponse oauthLogin(AppleOauthRequest oauthRequest);

	Member findMember(String token);

	void checkAuthentication(String token);
}
