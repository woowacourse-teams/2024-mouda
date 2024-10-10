package mouda.backend.member.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final AccessTokenProvider accessTokenProvider;
	private final MemberFinder memberFinder;

	public Member findMember(String token) {
		String socialId = accessTokenProvider.extractSocialId(token);
		return memberFinder.find(socialId);
	}

	public void checkAuthentication(String token) {
		accessTokenProvider.validateExpiration(token);
	}
}
