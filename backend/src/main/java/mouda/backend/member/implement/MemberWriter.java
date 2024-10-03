package mouda.backend.member.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberWriter {

	private final MemberRepository memberRepository;

	public Member append(Member member) {
		return memberRepository.save(member);
	}

	public void updateLoginDetail(long memberId, OauthType oauthType, String socialLoginId) {
		memberRepository.updateLoginDetail(memberId, oauthType, socialLoginId);
	}
}
