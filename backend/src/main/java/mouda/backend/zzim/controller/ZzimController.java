package mouda.backend.zzim.controller;

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
import mouda.backend.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.zzim.dto.request.ZzimUpdateRequest;
import mouda.backend.zzim.dto.response.ZzimCheckResponse;
import mouda.backend.zzim.service.ZzimService;

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
