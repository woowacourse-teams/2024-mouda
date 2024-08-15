package mouda.backend.darakbang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
import mouda.backend.darakbang.dto.response.CodeValidationResponse;
import mouda.backend.darakbang.dto.response.DarakbangResponses;
import mouda.backend.darakbang.dto.response.InvitationCodeResponse;
import mouda.backend.darakbang.service.DarakbangService;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/darakbang")
@RequiredArgsConstructor
public class DarakbangController implements DarakbangSwagger {

	private final DarakbangService darakbangService;

	@Override
	@PostMapping
	public ResponseEntity<RestResponse<Long>> createDarakbang(
		@Valid @RequestBody DarakbangCreateRequest darakbangCreateRequest,
		@LoginMember Member member
	) {
		Darakbang darakbang = darakbangService.createDarakbang(darakbangCreateRequest, member);

		return ResponseEntity.ok(new RestResponse<>(darakbang.getId()));
	}

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<DarakbangResponses>> findAllMyDarakbangs(@LoginMember Member member) {
		DarakbangResponses darakbangResponses = darakbangService.findAllMyDarakbangs(member);

		return ResponseEntity.ok(new RestResponse<>(darakbangResponses));
	}

	@Override
	@GetMapping("/{darakbangId}/code")
	public ResponseEntity<RestResponse<InvitationCodeResponse>> findInvitationCode(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	) {
		InvitationCodeResponse invitationCodeResponse = darakbangService.findInvitationCode(darakbangId, member);

		return ResponseEntity.ok(new RestResponse<>(invitationCodeResponse));
	}

	@Override
	@GetMapping("/validation")
	public ResponseEntity<RestResponse<CodeValidationResponse>> validateInvitationCode(@RequestParam String code) {
		CodeValidationResponse codeValidationResponse = darakbangService.validateCode(code);

		return ResponseEntity.ok(new RestResponse<>(codeValidationResponse));
	}
}
