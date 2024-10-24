package mouda.backend.bet.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.business.BetService;
import mouda.backend.bet.presentation.controller.swagger.BetSwagger;
import mouda.backend.bet.presentation.request.BetCreateRequest;
import mouda.backend.bet.presentation.response.BetCreateResponse;
import mouda.backend.bet.presentation.response.BetFindAllResponses;
import mouda.backend.bet.presentation.response.BetFindResponse;
import mouda.backend.bet.presentation.response.BetResultResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/bet")
@RequiredArgsConstructor
public class BetController implements BetSwagger {

	private final BetService betService;

	@Override
	@GetMapping
	public ResponseEntity<RestResponse<BetFindAllResponses>> findAll(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember) {
		BetFindAllResponses responses = betService.findAllBets(darakbangId);

		return ResponseEntity.ok(new RestResponse<>(responses));
	}

	@Override
	@GetMapping("/{betId}")
	public ResponseEntity<RestResponse<BetFindResponse>> findBetDetails(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		BetFindResponse response = betService.findBet(darakbangId, betId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(response));
	}

	@Override
	@PostMapping
	public ResponseEntity<RestResponse<BetCreateResponse>> createBet(
		@PathVariable Long darakbangId,
		@RequestBody BetCreateRequest request,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		long betId = betService.createBet(darakbangId, request, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(new BetCreateResponse(betId)));
	}

	@Override
	@PostMapping("/{betId}")
	public ResponseEntity<Void> participateBet(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		betService.participateBet(darakbangId, betId, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping("/{betId}/result")
	public ResponseEntity<RestResponse<BetResultResponse>> findBetResult(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		BetResultResponse response = betService.findBetResult(darakbangId, betId);

		return ResponseEntity.ok(new RestResponse<>(response));
	}

	@Override
	@PostMapping("/{betId}/result")
	public ResponseEntity<Void> drawBet(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		betService.drawBet(darakbangId, betId);

		return ResponseEntity.ok().build();
	}
}
