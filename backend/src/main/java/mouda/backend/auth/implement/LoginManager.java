package mouda.backend.auth.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.auth.implement.jwt.AccessTokenProvider;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.implement.MemberWriter;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginManager {

	private final MemberRepository memberRepository;
	private final AccessTokenProvider accessTokenProvider;
	private final MemberWriter memberWriter;

	public String processSocialLogin(OauthType oauthType, String socialLoginId) {
		Optional<Member> member = memberRepository.findByLoginDetail_SocialLoginId(socialLoginId);
		log.error("1번");
		if (member.isPresent()) {
			return accessTokenProvider.provide(member.get());
		}
		log.error("2번");
		Member newMember = Member.builder()
			.nickname("nickname")
			.loginDetail(new LoginDetail(oauthType, socialLoginId))
			.build();
		memberWriter.append(newMember);
		log.error("3번");
		return accessTokenProvider.provide(newMember);
	}
}
