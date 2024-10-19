package mouda.backend.member.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.member.domain.Member;
import mouda.backend.member.infrastructure.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberFinder {

	private final MemberRepository memberRepository;

	public Member findByIdentifier(String identifier) {
		return memberRepository.findByLoginDetail_Identifier(identifier)
			.orElseThrow(() -> new AuthException(HttpStatus.NOT_FOUND, AuthErrorMessage.MEMBER_NOT_FOUND));
	}

	public Member findByMemberId(long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new AuthException(HttpStatus.NOT_FOUND, AuthErrorMessage.MEMBER_NOT_FOUND));
	}
}
