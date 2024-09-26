package mouda.backend.member.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.implement.MemberWriter;
import mouda.backend.member.presentation.response.MemberFindResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberFinder memberFinder;
	private final MemberWriter memberWriter;

	@Transactional(readOnly = true)
	public MemberFindResponse findMyInfo(DarakbangMember darakbangMember) {
		String profile = "";
		return new MemberFindResponse(darakbangMember.getNickname(), profile);
	}

	public void remove(DarakbangMember darakbangMember) {
		Member member = memberFinder.find(darakbangMember.getMemberId());
		memberWriter.remove(member);
	}
}
