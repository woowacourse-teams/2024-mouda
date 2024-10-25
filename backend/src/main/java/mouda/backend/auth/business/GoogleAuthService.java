package mouda.backend.auth.business;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.implement.GoogleUserInfoProvider;
import mouda.backend.auth.implement.JoinManager;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.auth.presentation.request.GoogleLoginRequest;
import mouda.backend.auth.presentation.response.LoginResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;

@Service
@Transactional
@RequiredArgsConstructor
public class GoogleAuthService {

	private final JoinManager joinManager;
	private final GoogleUserInfoProvider userInfoProvider;
	private final MemberFinder memberFinder;
	private final AccessTokenProvider accessTokenProvider;
	private final MemberWriter memberWriter;

	public LoginResponse login(GoogleLoginRequest request) {
		String name = userInfoProvider.getName(request.idToken());
		String identifier = userInfoProvider.getIdentifier(request.idToken());
		Optional<Member> optionalMember = memberFinder.findOptionalActiveOrDeletedByIdentifier(identifier);

		if (optionalMember.isPresent()) {
			Member member = optionalMember.get();
			joinManager.rejoin(member);
			memberWriter.updateName(member.getId(), name);
			return new LoginResponse(accessTokenProvider.provide(member), member.isConverted());
		}
		Member joinedMember = joinManager.join(name, OauthType.GOOGLE, identifier);
		return new LoginResponse(accessTokenProvider.provide(joinedMember), joinedMember.isConverted());
	}
}
