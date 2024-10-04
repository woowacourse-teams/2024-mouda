package mouda.backend.auth.business;

import mouda.backend.auth.presentation.request.LegacyOauthRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;

public interface AuthService {

	LoginResponse oauthLogin(LegacyOauthRequest oauthRequest);

	Member findMember(String token);

	void checkAuthentication(String token);
}
