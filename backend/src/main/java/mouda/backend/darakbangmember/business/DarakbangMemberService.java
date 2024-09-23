package mouda.backend.darakbangmember.business;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.auth.exception.AuthErrorMessage;
import mouda.backend.auth.exception.AuthException;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponse;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangMemberService {

	private final DarakbangRepository darakbangRepository;
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

	public DarakbangMember findDarakbangMember(long darakbangId, Member member) {
		darakbangRepository.findById(darakbangId)
			.orElseThrow(
				() -> new DarakbangMemberException(HttpStatus.NOT_FOUND,
					DarakbangMemberErrorMessage.DARAKBANG_NOT_FOUND));

		return darakbangMemberRepository.findByDarakbangIdAndMemberId(darakbangId, member.getId())
			.orElseThrow(() -> new AuthException(HttpStatus.UNAUTHORIZED, AuthErrorMessage.DARAKBANG_NOT_ENTERED));
	}
}
