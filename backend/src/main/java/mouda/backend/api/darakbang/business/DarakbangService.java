package mouda.backend.api.darakbang.business;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.core.domain.darakbang.Darakbang;
import mouda.backend.api.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.api.darakbang.exception.DarakbangException;
import mouda.backend.api.darakbang.implement.InvitationCodeGenerator;
import mouda.backend.api.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.core.dto.darakbang.request.DarakbangCreateRequest;
import mouda.backend.core.dto.darakbang.request.DarakbangEnterRequest;
import mouda.backend.core.dto.darakbang.response.CodeValidationResponse;
import mouda.backend.core.dto.darakbang.response.DarakbangNameResponse;
import mouda.backend.core.dto.darakbang.response.DarakbangResponse;
import mouda.backend.core.dto.darakbang.response.DarakbangResponses;
import mouda.backend.core.dto.darakbang.response.InvitationCodeResponse;
import mouda.backend.core.domain.darakbang.DarakBangMemberRole;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.api.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.api.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.api.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.core.domain.member.Member;

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

		String invitationCode = generateInvitationCode();
		Darakbang entity = darakbangCreateRequest.toEntity(invitationCode);
		Darakbang darakbang = darakbangRepository.save(entity);

		DarakbangMember darakbangMember = DarakbangMember.builder()
			.darakbang(darakbang)
			.memberId(member.getId())
			.nickname(darakbangCreateRequest.nickname())
			.role(DarakBangMemberRole.MANAGER)
			.build();
		darakbangMemberRepository.save(darakbangMember);

		return darakbang;
	}

	private String generateInvitationCode() {
		String invitationCode = invitationCodeGenerator.generate();
		if (darakbangRepository.existsByCode(invitationCode)) {
			throw new DarakbangException(HttpStatus.INTERNAL_SERVER_ERROR, DarakbangErrorMessage.CODE_ALREADY_EXIST);
		}
		return invitationCode;
	}

	@Transactional(readOnly = true)
	public DarakbangResponses findAllMyDarakbangs(Member member) {
		List<DarakbangMember> darakbangMembers = darakbangMemberRepository.findAllByMemberId(member.getId());
		List<DarakbangResponse> responses = darakbangMembers.stream()
			.map(DarakbangResponse::toResponse)
			.toList();

		return DarakbangResponses.toResponse(responses);
	}

	@Transactional(readOnly = true)
	public InvitationCodeResponse findInvitationCode(Long darakbangId, DarakbangMember member) {
		if (member.isNotManager()) {
			throw new DarakbangMemberException(HttpStatus.FORBIDDEN, DarakbangMemberErrorMessage.NOT_ALLOWED_TO_READ);
		}

		Darakbang darakbang = darakbangRepository.findById(darakbangId)
			.orElseThrow(() -> new DarakbangException(HttpStatus.NOT_FOUND, DarakbangErrorMessage.DARAKBANG_NOT_FOUND));

		return InvitationCodeResponse.toResponse(darakbang);
	}

	@Transactional(readOnly = true)
	public CodeValidationResponse validateCode(String code) {
		Darakbang darakbang = darakbangRepository.findByCode(code)
			.orElseThrow(() -> new DarakbangException(HttpStatus.BAD_REQUEST, DarakbangErrorMessage.INVALID_CODE));

		return CodeValidationResponse.toResponse(darakbang);
	}

	public Darakbang enter(String code, DarakbangEnterRequest request, Member member) {
		Darakbang darakbang = darakbangRepository.findByCode(code)
			.orElseThrow(() -> new DarakbangException(HttpStatus.NOT_FOUND, DarakbangErrorMessage.DARAKBANG_NOT_FOUND));

		if (darakbangMemberRepository.existsByDarakbangIdAndNickname(darakbang.getId(), request.nickname())) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST,
				DarakbangMemberErrorMessage.NICKNAME_ALREADY_EXIST);
		}
		if (darakbangMemberRepository.existsByDarakbangIdAndMemberId(darakbang.getId(), member.getId())) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST,
				DarakbangMemberErrorMessage.MEMBER_ALREADY_EXIST);
		}

		DarakbangMember entity = request.toEntity(darakbang, member);
		darakbangMemberRepository.save(entity);

		return darakbang;
	}

	@Transactional(readOnly = true)
	public DarakbangNameResponse findDarakbangName(Long darakbangId) {
		Darakbang darakbang = darakbangRepository.findById(darakbangId)
			.orElseThrow(() -> new DarakbangException(HttpStatus.NOT_FOUND, DarakbangErrorMessage.DARAKBANG_NOT_FOUND));

		return DarakbangNameResponse.toResponse(darakbang);
	}
}
