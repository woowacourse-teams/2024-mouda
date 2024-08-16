package mouda.backend.darakbangmember.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.dto.response.DarakbangMemberResponse;
import mouda.backend.darakbangmember.dto.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Service
@RequiredArgsConstructor
public class DarakbangMemberService {

	private final DarakbangMemberRepository darakbangMemberRepository;

	public DarakbangMemberResponses findAllDarakbangMembers(Long darakbangId, Member member) {
		DarakbangMember darakbangMember = darakbangMemberRepository
			.findByDarakbangIdAndMemberId(darakbangId, member.getId())
			.orElseThrow(
				() -> new DarakbangMemberException(HttpStatus.FORBIDDEN, DarakbangMemberErrorMessage.MEMBER_NOT_EXIST));

		if (darakbangMember.isNotManager()) {
			throw new DarakbangMemberException(HttpStatus.FORBIDDEN, DarakbangMemberErrorMessage.NOT_ALLOWED_TO_READ);
		}

		List<DarakbangMember> darakbangMembers = darakbangMemberRepository.findAllByDarakbangId(darakbangId);
		List<DarakbangMemberResponse> memberResponses = darakbangMembers.stream()
			.map(DarakbangMemberResponse::toResponse)
			.toList();

		return DarakbangMemberResponses.toResponse(memberResponses);
	}
}
