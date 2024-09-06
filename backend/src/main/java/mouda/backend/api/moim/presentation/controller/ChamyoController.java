package mouda.backend.api.moim.presentation.controller;

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
import mouda.backend.api.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.api.common.response.RestResponse;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.api.moim.business.ChamyoService;
import mouda.backend.api.moim.presentation.controller.swagger.ChamyoSwagger;
import mouda.backend.core.dto.moim.request.chamyo.ChamyoCancelRequest;
import mouda.backend.core.dto.moim.request.chamyo.MoimChamyoRequest;
import mouda.backend.core.dto.moim.response.chamyo.ChamyoFindAllResponses;
import mouda.backend.core.dto.moim.response.chamyo.MoimRoleFindResponse;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/chamyo")
@RequiredArgsConstructor
public class ChamyoController implements ChamyoSwagger {

	private final ChamyoService chamyoService;

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<MoimRoleFindResponse>> findMoimRoleByMember(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam Long moimId
	) {
		MoimRoleFindResponse moimRoleFindResponse = chamyoService.findMoimRole(darakbangId, moimId, member);

		return ResponseEntity.ok().body(new RestResponse<>(moimRoleFindResponse));
	}

	@Override
	@GetMapping("/all")
	public ResponseEntity<RestResponse<ChamyoFindAllResponses>> findAllChamyoByMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam Long moimId
	) {
		ChamyoFindAllResponses chamyoFindAllResponses = chamyoService.findAllChamyo(darakbangId, moimId);

		return ResponseEntity.ok().body(new RestResponse<>(chamyoFindAllResponses));
	}

	@Override
	@PostMapping
	public ResponseEntity<Void> chamyoMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody MoimChamyoRequest request
	) {
		chamyoService.chamyoMoim(darakbangId, request.moimId(), member);

		return ResponseEntity.ok().build();
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Void> cancelChamyo(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody ChamyoCancelRequest request
	) {
		chamyoService.cancelChamyo(darakbangId, request.moimId(), member);

		return ResponseEntity.ok().build();
	}
}
