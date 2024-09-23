package mouda.backend.member.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.domain.Member;
import mouda.backend.member.exception.MemberErrorMessage;
import mouda.backend.member.exception.MemberException;
import mouda.backend.member.infrastructure.MemberRepository;
import mouda.backend.member.presentation.response.MemberInfoResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MemberInfoResponse findMyInfo(DarakbangMember darakbangMember) {
		String profile = "";
		return new MemberInfoResponse(darakbangMember.getNickname(), profile);
	}

	@Transactional(readOnly = true)
	public Member findMember(long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(HttpStatus.BAD_REQUEST, MemberErrorMessage.MEMBER_NOT_FOUND));
	}
}
