package mouda.backend.zzim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.zzim.dto.response.ZzimCheckResponse;
import mouda.backend.zzim.dto.request.ZzimUpdateRequest;
import mouda.backend.zzim.service.ZzimService;

@RestController
@RequestMapping("/v1/zzim")
@RequiredArgsConstructor
public class ZzimController implements ZzimSwagger {

	private final ZzimService zzimService;

	@Override
	@GetMapping("/me")
	public ResponseEntity<RestResponse<ZzimCheckResponse>> checkZzimByMoimAndMember(
		@RequestParam Long moimId, @LoginMember Member member
	) {
		ZzimCheckResponse zzimCheckResponse = zzimService.checkZzimByMember(moimId, member);

		return ResponseEntity.ok().body(new RestResponse<>(zzimCheckResponse));
	}

	@Override
	@PostMapping
	public ResponseEntity<Void> updateZzim(@Valid @RequestBody ZzimUpdateRequest request, @LoginMember Member member) {
		zzimService.updateZzim(request, member);

		return ResponseEntity.ok().build();
	}
}
