package mouda.backend.chamyo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.chamyo.dto.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.MoimRoleFindResponse;
import mouda.backend.chamyo.service.ChamyoService;
import mouda.backend.common.RestResponse;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/chamyo")
@RequiredArgsConstructor
public class ChamyoController implements ChamyoSwagger {

	private final ChamyoService chamyoService;

	@Override
	@GetMapping("/me")
	public ResponseEntity<RestResponse<MoimRoleFindResponse>> findMoimRoleByMember(
		@RequestParam Long moimId, Member member
	) {
		MoimRoleFindResponse moimRoleFindResponse = chamyoService.findMoimRole(moimId, member);

		return ResponseEntity.ok().body(new RestResponse<>(moimRoleFindResponse));
	}

	@Override
	@GetMapping("/all")
	public ResponseEntity<RestResponse<ChamyoFindAllResponses>> findAllChamyo(@RequestParam Long moimId) {
		ChamyoFindAllResponses chamyoFindAllResponses = chamyoService.findAllChamyo(moimId);

		return ResponseEntity.ok().body(new RestResponse<>(chamyoFindAllResponses));
	}
}
