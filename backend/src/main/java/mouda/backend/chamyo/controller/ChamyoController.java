package mouda.backend.chamyo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.dto.request.ChamyoCancelRequest;
import mouda.backend.chamyo.dto.request.MoimChamyoRequest;
import mouda.backend.chamyo.dto.response.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.response.MoimRoleFindResponse;
import mouda.backend.chamyo.service.ChamyoService;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/chamyo")
@RequiredArgsConstructor
public class ChamyoController implements ChamyoSwagger {

	private final ChamyoService chamyoService;

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<MoimRoleFindResponse>> findMoimRoleByMember(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@RequestParam Long moimId
	) {
		MoimRoleFindResponse moimRoleFindResponse = chamyoService.findMoimRole(moimId, member);

		return ResponseEntity.ok().body(new RestResponse<>(moimRoleFindResponse));
	}

	@Override
	@GetMapping("/all")
	public ResponseEntity<RestResponse<ChamyoFindAllResponses>> findAllChamyoByMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@RequestParam Long moimId
	) {
		ChamyoFindAllResponses chamyoFindAllResponses = chamyoService.findAllChamyo(moimId);

		return ResponseEntity.ok().body(new RestResponse<>(chamyoFindAllResponses));
	}

	@Override
	@PostMapping
	public ResponseEntity<Void> chamyoMoim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody MoimChamyoRequest request
	) {
		chamyoService.chamyoMoim(request, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Void> cancelChamyo(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody ChamyoCancelRequest request
	) {
		chamyoService.cancelChamyo(request, member);

		return ResponseEntity.ok().build();
	}
}
