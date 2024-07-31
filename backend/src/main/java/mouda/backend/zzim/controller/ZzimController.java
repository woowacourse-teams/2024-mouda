package mouda.backend.zzim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.zzim.dto.ZzimCheckResponse;
import mouda.backend.zzim.service.ZzimService;

@RestController
@RequestMapping("/v1/zzim")
@RequiredArgsConstructor
public class ZzimController implements ZzimSwagger {

	private final ZzimService zzimService;

	@Override
	@GetMapping("/me")
	public ResponseEntity<RestResponse<ZzimCheckResponse>> checkZzimByMoimAndMember(
		@RequestParam Long moimId, Member member
	) {
		ZzimCheckResponse zzimCheckResponse = zzimService.checkZzimByMember(moimId, member);

		return ResponseEntity.ok().body(new RestResponse<>(zzimCheckResponse));
	}
}
