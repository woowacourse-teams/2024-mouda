package mouda.backend.darakbangmember.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMembers;
import mouda.backend.darakbangmember.implement.DarakbangMemberFinder;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangMemberService {

	private final DarakbangMemberFinder darakbangMemberFinder;
	private final DarakbangFinder darakbangFinder;

	@Transactional(readOnly = true)
	public DarakbangMemberResponses findAllDarakbangMembers(Long darakbangId, DarakbangMember member) {
		DarakbangMembers darakbangMembers = darakbangMemberFinder.findAllDarakbangMembers(darakbangId);

		return DarakbangMemberResponses.toResponse(darakbangMembers);
	}

	@Transactional(readOnly = true)
	public DarakbangMemberRoleResponse findDarakbangMemberRole(Long darakbangId, Member member) {
		DarakBangMemberRole role = darakbangMemberFinder.findDarakbangMemberRole(darakbangId, member.getId());

		return DarakbangMemberRoleResponse.toResponse(role);
	}

	public DarakbangMember findDarakbangMember(long darakbangId, Member member) {
		Darakbang darakbang = darakbangFinder.findById(darakbangId);
		return darakbangMemberFinder.find(darakbang, member);
	}
}
