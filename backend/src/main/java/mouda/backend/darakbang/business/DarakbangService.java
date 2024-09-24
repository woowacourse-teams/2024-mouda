package mouda.backend.darakbang.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.domain.Darakbangs;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.darakbang.implement.DarakbangValidator;
import mouda.backend.darakbang.implement.DarakbangWriter;
import mouda.backend.darakbang.presentation.request.DarakbangCreateRequest;
import mouda.backend.darakbang.presentation.request.DarakbangEnterRequest;
import mouda.backend.darakbang.presentation.response.CodeValidationResponse;
import mouda.backend.darakbang.presentation.response.DarakbangNameResponse;
import mouda.backend.darakbang.presentation.response.DarakbangResponses;
import mouda.backend.darakbang.presentation.response.InvitationCodeResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.implement.DarakbangMemberWriter;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberValidator;

@Service
@Transactional
@RequiredArgsConstructor
public class DarakbangService {

	private final DarakbangWriter darakbangWriter;
	private final DarakbangFinder darakbangFinder;
	private final DarakbangValidator darakbangValidator;
	private final DarakbangMemberWriter darakbangMemberWriter;
	private final MemberValidator memberValidator;

	public Darakbang createDarakbang(DarakbangCreateRequest darakbangCreateRequest, Member member) {
		darakbangValidator.validateAlreadyExistsName(darakbangCreateRequest.name());
		Darakbang darakbang = darakbangWriter.save(darakbangCreateRequest.name());
		darakbangMemberWriter.saveManager(darakbang, darakbangCreateRequest.nickname(), member);

		return darakbang;
	}

	@Transactional(readOnly = true)
	public DarakbangResponses findAllMyDarakbangs(Member member) {
		Darakbangs darakbangs = darakbangFinder.findAllMyDarakbangs(member);

		return DarakbangResponses.toResponse(darakbangs);
	}

	@Transactional(readOnly = true)
	public InvitationCodeResponse findInvitationCode(Long darakbangId, DarakbangMember member) {
		memberValidator.validateNotManager(member);
		Darakbang darakbang = darakbangFinder.findById(darakbangId);

		return InvitationCodeResponse.toResponse(darakbang);
	}

	@Transactional(readOnly = true)
	public CodeValidationResponse validateCode(String code) {
		Darakbang darakbang = darakbangFinder.findByCode(code);

		return CodeValidationResponse.toResponse(darakbang);
	}

	public Darakbang enter(String code, DarakbangEnterRequest request, Member member) {
		Darakbang darakbang = darakbangFinder.findByCode(code);
		darakbangValidator.validateCanEnterDarakbang(darakbang, request.nickname(), member);
		darakbangMemberWriter.saveMember(darakbang, request.nickname(), member);

		return darakbang;
	}

	@Transactional(readOnly = true)
	public DarakbangNameResponse findDarakbangName(Long darakbangId) {
		Darakbang darakbang = darakbangFinder.findById(darakbangId);

		return DarakbangNameResponse.toResponse(darakbang);
	}
}
