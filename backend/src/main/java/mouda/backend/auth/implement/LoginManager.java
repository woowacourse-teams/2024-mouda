package mouda.backend.auth.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
public class LoginManager {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final MemberWriter memberWriter;

	public String processKakaoLogin(long kakaoId) {
		Optional<Member> member = memberRepository.findByKakaoId(kakaoId);

		if (member.isPresent()) {
			return jwtProvider.createToken(member.get());
		}

		Member newMember = Member.builder()
			.kakaoId(kakaoId)
			.build();
		memberWriter.append(newMember);

		return jwtProvider.createToken(newMember);
	}
}
