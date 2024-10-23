package mouda.backend.member.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.LoginDetail;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberWriter {

	private final MemberRepository memberRepository;

	public Member append(Member member) {
		return memberRepository.save(member);
	}

	public void updateLoginDetail(Member member, LoginDetail loginDetail) {
		member.updateLoginDetail(loginDetail);
		memberRepository.save(member);
	}

	public void updateName(long memberId, String name) {
		memberRepository.updateName(memberId, name);
	}

	public void withdraw(Member member) {
		member.withdraw();
		memberRepository.save(member);
	}

	public void delete(Member alternation) {
		memberRepository.delete(alternation);
	}

	public void deprecate(Member member) {
		member.deprecate();
		memberRepository.save(member);
	}
}
