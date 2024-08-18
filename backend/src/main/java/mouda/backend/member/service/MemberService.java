package mouda.backend.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.dto.response.MemberFindResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	@Transactional(readOnly = true)
	public MemberFindResponse findMyInfo(DarakbangMember member) {
		String profile = "";
		return new MemberFindResponse(member.getNickname(), profile);
	}
}
