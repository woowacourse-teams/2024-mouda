package mouda.backend.auth.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.implement.AppleUserInfoProvider;
import mouda.backend.auth.implement.JoinManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
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

	public String login(String idToken, String user) {
		String identifier = userInfoProvider.getIdentifier(idToken);
		if (user != null) {
			return handleNewUser(user, identifier);
		}
		return handleExistingUser(identifier);
	}

	private String handleNewUser(String user, String identifier) {
		Member joinedMember = join(identifier, user);
		return accessTokenProvider.provide(joinedMember);
	}

	private String handleExistingUser(String identifier) {
		Member member = memberFinder.getByIdentifier(identifier);
		if (member != null) {
			joinManager.rejoin(member);
			return accessTokenProvider.provide(member);
		}
		throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.CANNOT_FIND_APPLE_MEMBER);
	}

	public Member join(String identifier, String user) {
		String name = userInfoProvider.getName(user);
		return joinManager.join(name, OauthType.APPLE, identifier);
	}
}
