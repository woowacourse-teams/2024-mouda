package mouda.backend.auth.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
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

	public String processKakaoLogin(long kakaoId) {
		Optional<Member> member = memberRepository.findByLoginDetail_SocialLoginId(kakaoId);

		if (member.isPresent()) {
			return accessTokenProvider.provide(member.get());
		}

		Member newMember = Member.builder()
			.nickname("nickname")
			.loginDetail(new LoginDetail(OauthType.KAKAO, kakaoId))
			.build();
		memberWriter.append(newMember);

		return accessTokenProvider.provide(newMember);
	}
}
