package mouda.backend.bet.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.business.BetService;
import mouda.backend.bet.presentation.response.BetFindAllResponses;
import mouda.backend.bet.presentation.response.BetFindResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/bet")
@RequiredArgsConstructor
public class BetController {

	private final BetService betService;

	@GetMapping
	public ResponseEntity<RestResponse<BetFindAllResponses>> findAll(@PathVariable Long darakbangId, @LoginDarakbangMember DarakbangMember darakbangMember) {
		BetFindAllResponses responses = betService.findAllBets(darakbangId);

		return ResponseEntity.ok(new RestResponse<>(responses));
	}

	@GetMapping("/{betId}")
	public ResponseEntity<RestResponse<BetFindResponse>> findBetDetails(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		BetFindResponse response = betService.findBet(darakbangId, betId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(response));
	}
}
