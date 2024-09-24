package mouda.backend.auth.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Component
@RequiredArgsConstructor
public class DarakbangMemberFinder {

	private final DarakbangMemberRepository darakbangMemberRepository;

	public DarakbangMember find(Darakbang darakbang, Member member) {
		return darakbangMemberRepository.findByDarakbangIdAndMemberId(darakbang.getId(), member.getId())
			.orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.DARAKBANG_NOT_ENTERED));
	}
}
