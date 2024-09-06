package mouda.backend.api.darakbangmember.business;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.api.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.api.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.api.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.core.dto.darakbang.response.DarakbangMemberResponse;
import mouda.backend.core.dto.darakbang.response.DarakbangMemberResponses;
import mouda.backend.core.dto.darakbang.response.DarakbangMemberRoleResponse;
import mouda.backend.core.domain.member.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangMemberService {

	private final DarakbangMemberRepository darakbangMemberRepository;

	@Transactional(readOnly = true)
	public DarakbangMemberResponses findAllDarakbangMembers(Long darakbangId, DarakbangMember member) {
		if (member.isNotManager()) {
			throw new DarakbangMemberException(HttpStatus.FORBIDDEN, DarakbangMemberErrorMessage.NOT_ALLOWED_TO_READ);
		}

		List<DarakbangMember> darakbangMembers = darakbangMemberRepository.findAllByDarakbangId(darakbangId);
		List<DarakbangMemberResponse> memberResponses = darakbangMembers.stream()
			.map(DarakbangMemberResponse::toResponse)
			.toList();

		return DarakbangMemberResponses.toResponse(memberResponses);
	}

	@Transactional(readOnly = true)
	public DarakbangMemberRoleResponse findDarakbangMemberRole(Long darakbangId, Member member) {
		Optional<DarakbangMember> optionalDarakbangMember = darakbangMemberRepository.findByDarakbangIdAndMemberId(
			darakbangId, member.getId());

		if (optionalDarakbangMember.isPresent()) {
			DarakbangMember darakbangMember = optionalDarakbangMember.get();
			return DarakbangMemberRoleResponse.toResponse(darakbangMember.getRole());
		}
		return DarakbangMemberRoleResponse.toResponse();
	}
}
