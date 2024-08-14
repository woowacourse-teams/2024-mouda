package mouda.backend.darakbang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
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
		@RequestBody DarakbangCreateRequest darakbangCreateRequest,
		@LoginMember Member member
	) {
		Darakbang darakbang = darakbangService.createDarakbang(darakbangCreateRequest, member);
		
		return ResponseEntity.ok(new RestResponse<>(darakbang.getId()));
	}
}