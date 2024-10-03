package mouda.backend.auth.implement;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberWriter;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
public class LoginManager {

	private final MemberRepository memberRepository;
	private final AccessTokenProvider accessTokenProvider;
	private final MemberWriter memberWriter;

	public String processSocialLogin(OauthType oauthType, String socialLoginId) {
		Optional<Member> member = memberRepository.findByLoginDetail_SocialLoginId(socialLoginId);

		if (member.isPresent()) {
			return accessTokenProvider.provide(member.get());
		}

		if (oauthType == OauthType.KAKAO) {
			throw new AuthException(HttpStatus.BAD_REQUEST, AuthErrorMessage.KAKAO_CANNOT_SIGNUP);
		}
		Member newMember = Member.builder()
			.nickname("nickname")
			.loginDetail(new LoginDetail(oauthType, socialLoginId))
			.build();
		memberWriter.append(newMember);

		return accessTokenProvider.provide(newMember);
	}
}
