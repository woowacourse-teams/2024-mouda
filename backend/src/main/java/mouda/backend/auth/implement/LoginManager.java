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

	public LoginProcessResult processSocialLogin(OauthType oauthType, String socialLoginId) {
		Optional<Member> member = memberRepository.findByLoginDetail_SocialLoginId(socialLoginId);

		if (member.isPresent()) {
			return new LoginProcessResult(member.get().getId(), accessTokenProvider.provide(member.get()));
		}

		if (oauthType == OauthType.KAKAO) {
			throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.KAKAO_CANNOT_SIGNUP);
		}
		Member newMember = Member.builder()
			.nickname("nickname")
			.loginDetail(new LoginDetail(oauthType, socialLoginId))
			.build();
		memberWriter.append(newMember);

		return new LoginProcessResult(newMember.getId(), accessTokenProvider.provide(newMember));
	}

	public String updateOauth(long memberId, OauthType oauthType, String socialLoginId) {
		Member member = memberFinder.find(memberId);
		memberWriter.updateLoginDetail(memberId, oauthType, socialLoginId);

		return accessTokenProvider.provide(member);
	}
}
