package mouda.backend.moim.presentation.controller;

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
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.business.ZzimService;
import mouda.backend.moim.presentation.controller.swagger.ZzimSwagger;
import mouda.backend.moim.presentation.request.zzim.ZzimUpdateRequest;
import mouda.backend.moim.presentation.response.zzim.ZzimCheckResponse;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/zzim")
@RequiredArgsConstructor
public class ZzimController implements ZzimSwagger {

	private final ZzimService zzimService;

	@Override
	@GetMapping("/mine")
	public ResponseEntity<RestResponse<ZzimCheckResponse>> checkZzimByMoimAndMember(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam Long moimId
	) {
		ZzimCheckResponse zzimCheckResponse = zzimService.checkZzimByMember(darakbangId, moimId, member);

		return ResponseEntity.ok().body(new RestResponse<>(zzimCheckResponse));
	}

	@Override
	@PostMapping
	public ResponseEntity<Void> updateZzim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody ZzimUpdateRequest request
	) {
		zzimService.updateZzim(darakbangId, request.moimId(), member);

		return ResponseEntity.ok().build();
	}
}
