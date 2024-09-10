package mouda.backend.darakbangmember.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMembers;
import mouda.backend.darakbangmember.implement.DarakbangMemberFinder;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangMemberService {

	private final MemberValidator memberValidator;
	private final DarakbangMemberFinder darakbangMemberFinder;

	@Transactional(readOnly = true)
	public DarakbangMemberResponses findAllDarakbangMembers(Long darakbangId, DarakbangMember member) {
		memberValidator.validateNotManager(member);
		DarakbangMembers darakbangMembers = darakbangMemberFinder.findAllDarakbangMembers(darakbangId);

		return DarakbangMemberResponses.toResponse(darakbangMembers);
	}

	@Transactional(readOnly = true)
	public DarakbangMemberRoleResponse findDarakbangMemberRole(Long darakbangId, Member member) {
		DarakBangMemberRole role = darakbangMemberFinder.findDarakbangMemberRole(darakbangId, member.getId());

		return DarakbangMemberRoleResponse.toResponse(role);
	}
}
