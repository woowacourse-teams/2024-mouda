package mouda.backend.auth.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.implement.AppleUserInfoProvider;
import mouda.backend.auth.implement.JoinManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;

@Service
@RequiredArgsConstructor
public class AppleAuthService {

	private final JoinManager joinManager;
	private final AppleUserInfoProvider userInfoProvider;
	private final MemberFinder memberFinder;
	private final AccessTokenProvider accessTokenProvider;

	public LoginResponse login(String idToken, String user) {
		String identifier = userInfoProvider.getIdentifier(idToken);
		if (user != null) {
			return handleNewUser(user, identifier);
		}
		return handleExistingUser(identifier);
	}

	private LoginResponse handleNewUser(String user, String identifier) {
		Member joinedMember = join(identifier, user);
		return new LoginResponse(accessTokenProvider.provide(joinedMember), joinedMember.isConverted());
	}

	private Member join(String identifier, String user) {
		String name = userInfoProvider.getName(user);
		return joinManager.join(name, OauthType.APPLE, identifier);
	}

	private LoginResponse handleExistingUser(String identifier) {
		Member member = memberFinder.findActiveOrDeletedByIdentifier(identifier);
		if (member != null) {
			joinManager.rejoin(member);
			return new LoginResponse(accessTokenProvider.provide(member), member.isConverted());
		}
		throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.CANNOT_FIND_APPLE_MEMBER);
	}
}
