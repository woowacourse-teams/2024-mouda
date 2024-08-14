package mouda.backend.darakbang.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
import mouda.backend.darakbang.dto.response.DarakbangResponse;
import mouda.backend.darakbang.dto.response.DarakbangResponses;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.repository.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakBangMemberRole;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangService {

	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberRepository darakbangMemberRepository;
	private final InvitationCodeGenerator invitationCodeGenerator;

	public Darakbang createDarakbang(DarakbangCreateRequest darakbangCreateRequest, Member member) {
		if (darakbangRepository.existsByName(darakbangCreateRequest.name())) {
			throw new DarakbangException(HttpStatus.BAD_REQUEST, DarakbangErrorMessage.NAME_ALREADY_EXIST);
		}

		String invitationCode = invitationCodeGenerator.generate();
		Darakbang entity = darakbangCreateRequest.toEntity(invitationCode);
		Darakbang darakbang = darakbangRepository.save(entity);

		DarakbangMember darakbangMember = DarakbangMember.builder()
			.darakbang(darakbang)
			.member(member)
			.nickname(darakbangCreateRequest.nickname())
			.role(DarakBangMemberRole.MANAGER)
			.build();
		darakbangMemberRepository.save(darakbangMember);

		return darakbang;
	}

	public DarakbangResponses findAllMyDarakbangs(Member member) {
		List<DarakbangMember> darakbangMembers = darakbangMemberRepository.findAllByMemberId(member.getId());
		List<DarakbangResponse> responses = darakbangMembers.stream()
			.map(DarakbangResponse::toResponse)
			.toList();

		return DarakbangResponses.toResponse(responses);
	}
}
