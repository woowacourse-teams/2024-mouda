package mouda.backend.darakbang.business;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.domain.Darakbangs;
import mouda.backend.darakbang.exception.DarakbangErrorMessage;
import mouda.backend.darakbang.exception.DarakbangException;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.darakbang.implement.DarakbangWriter;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbang.inplement.DarakbangValidator;
import mouda.backend.darakbang.presentation.request.DarakbangCreateRequest;
import mouda.backend.darakbang.presentation.request.DarakbangEnterRequest;
import mouda.backend.darakbang.presentation.response.CodeValidationResponse;
import mouda.backend.darakbang.presentation.response.DarakbangNameResponse;
import mouda.backend.darakbang.presentation.response.DarakbangResponses;
import mouda.backend.darakbang.presentation.response.InvitationCodeResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.exception.DarakbangMemberErrorMessage;
import mouda.backend.darakbangmember.exception.DarakbangMemberException;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangService {

	private final DarakbangRepository darakbangRepository;
	private final DarakbangMemberRepository darakbangMemberRepository;

	private final DarakbangValidator darakbangValidator;
	private final DarakbangWriter darakbangWriter;
	private final DarakbangFinder darakbangFinder;
	private final MemberValidator memberValidator;

	public Darakbang createDarakbang(DarakbangCreateRequest darakbangCreateRequest, Member member) {
		darakbangValidator.validateAlreadyExistsName(darakbangCreateRequest.name());

		return darakbangWriter.save(darakbangCreateRequest.name(), darakbangCreateRequest.nickname(), member);
	}

	@Transactional(readOnly = true)
	public DarakbangResponses findAllMyDarakbangs(Member member) {
		Darakbangs darakbangs = darakbangFinder.findAllMyDarakbangs(member);

		return DarakbangResponses.toResponse(darakbangs);
	}

	@Transactional(readOnly = true)
	public InvitationCodeResponse findInvitationCode(Long darakbangId, DarakbangMember member) {
		memberValidator.validateNotManger(member);
		Darakbang darakbang = darakbangFinder.findById(darakbangId);

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
		try {
			darakbangMemberRepository.save(entity);
		} catch (DataIntegrityViolationException exception) {
			throw new DarakbangMemberException(HttpStatus.BAD_REQUEST,
				DarakbangMemberErrorMessage.MEMBER_ALREADY_EXIST);
		}

		return darakbang;
	}

	@Transactional(readOnly = true)
	public DarakbangNameResponse findDarakbangName(Long darakbangId) {
		Darakbang darakbang = darakbangRepository.findById(darakbangId)
			.orElseThrow(() -> new DarakbangException(HttpStatus.NOT_FOUND, DarakbangErrorMessage.DARAKBANG_NOT_FOUND));

		return DarakbangNameResponse.toResponse(darakbang);
	}
}
