package mouda.backend.please.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.PleaseCreateRequest;
import mouda.backend.please.service.PleaseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/please")
public class PleaseController {

	private final PleaseService pleaseService;

	@PostMapping
	public ResponseEntity<RestResponse<Long>> createPlease(
		@LoginMember Member member,
		@Valid @RequestBody PleaseCreateRequest pleaseCreateRequest
	) {
		Please please = pleaseService.createPlease(member, pleaseCreateRequest);

		return ResponseEntity.ok().body(new RestResponse<>(please.getId()));
	}
}
