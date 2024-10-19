package mouda.backend.auth.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Component
@RequiredArgsConstructor
public class LoginManager {

	private final AccessTokenProvider accessTokenProvider;
	private final MemberWriter memberWriter;
	private final MemberFinder memberFinder;

	public String updateOauth(long memberId, OauthType oauthType, String identifier) {
		Member member = memberFinder.findByIdentifier(identifier);
		memberWriter.updateLoginDetail(memberId, oauthType, identifier);

		return accessTokenProvider.provide(member);
	}
}
