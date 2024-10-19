package mouda.backend.auth.implement;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.business.result.LoginProcessResult;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
public class LoginManager {

	private final MemberRepository memberRepository;
	private final AccessTokenProvider accessTokenProvider;
	private final MemberWriter memberWriter;
	private final MemberFinder memberFinder;

	public LoginProcessResult processSocialLogin(OauthType oauthType, String socialLoginId, String name) {
		Optional<Member> member = memberRepository.findByLoginDetail_SocialLoginId(socialLoginId);
		if (member.isEmpty()) {
			return signup(oauthType, socialLoginId, name);
		}
		if (member.get().isDeleted()) {
			member.get().reSignup(); // TODO: 더티 체킹이 가능할까?
		}
		return new LoginProcessResult(accessTokenProvider.provide(member.get()));
	}

	private LoginProcessResult signup(OauthType oauthType, String socialLoginId, String name) {
		if (OauthType.KAKAO.equals(oauthType)) {
			throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.KAKAO_CANNOT_SIGNUP);
		}
		Member member = memberWriter.append(new Member(name, new LoginDetail(oauthType, socialLoginId)));
		return new LoginProcessResult(accessTokenProvider.provide(member));
	}

	public String updateOauth(long memberId, OauthType oauthType, String socialLoginId) {
		Member member = memberFinder.findBySocialId(socialLoginId);
		memberWriter.updateLoginDetail(memberId, oauthType, socialLoginId);

		return accessTokenProvider.provide(member);
	}
}
