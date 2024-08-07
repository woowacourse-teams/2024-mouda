package mouda.backend.please.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.please.dto.request.InterestUpdateRequest;
import mouda.backend.please.service.InterestService;

@RestController
@RequestMapping("/v1/interest")
@RequiredArgsConstructor
public class InterestController implements InterestSwagger {

	private final InterestService interestService;

	@Override
	@PostMapping
	public ResponseEntity<Void> updateInterest(@LoginMember Member member, @RequestBody InterestUpdateRequest request) {
		interestService.updateInterest(member, request);

		return ResponseEntity.ok().build();
	}
}
