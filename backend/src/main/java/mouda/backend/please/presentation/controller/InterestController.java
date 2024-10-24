package mouda.backend.please.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.business.InterestService;
import mouda.backend.please.presentation.controller.swagger.InterestSwagger;
import mouda.backend.please.presentation.request.InterestUpdateRequest;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/interest")
@RequiredArgsConstructor
public class InterestController implements InterestSwagger {

	private final InterestService interestService;

	@Override
	@PostMapping
	public ResponseEntity<Void> updateInterest(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody InterestUpdateRequest request
	) {
		interestService.updateInterest(darakbangId, member, request);

		return ResponseEntity.ok().build();
	}
}
