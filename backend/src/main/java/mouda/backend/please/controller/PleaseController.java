package mouda.backend.please.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.please.dto.response.PleaseFindAllResponses;
import mouda.backend.please.service.PleaseService;

@RestController
@RequestMapping("/v1/please")
@RequiredArgsConstructor
public class PleaseController {

	private final PleaseService pleaseService;

	@GetMapping
	public ResponseEntity<RestResponse<PleaseFindAllResponses>> findAllPlease(@LoginMember Member member) {
		PleaseFindAllResponses responses = pleaseService.findAllPlease(member);

		return ResponseEntity.ok().body(new RestResponse<>(responses));
	}
}
