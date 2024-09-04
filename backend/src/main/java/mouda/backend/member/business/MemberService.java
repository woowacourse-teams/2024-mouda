package mouda.backend.member.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.presentation.response.MemberFindResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	@Transactional(readOnly = true)
	public MemberFindResponse findMyInfo(DarakbangMember darakbangMember) {
		String profile = "";
		return new MemberFindResponse(darakbangMember.getNickname(), profile);
	}
}
