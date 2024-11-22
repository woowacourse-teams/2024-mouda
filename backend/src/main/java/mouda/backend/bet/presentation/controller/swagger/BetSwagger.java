package mouda.backend.bet.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.bet.presentation.request.BetCreateRequest;
import mouda.backend.bet.presentation.response.BetCreateResponse;
import mouda.backend.bet.presentation.response.BetFindAllResponses;
import mouda.backend.bet.presentation.response.BetFindResponse;
import mouda.backend.bet.presentation.response.BetResultResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

public interface BetSwagger {

	@Operation(summary = "베팅 목록 조회", description = "다락방의 모든 베팅을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "베팅 목록 조회 성공")
	})
	ResponseEntity<RestResponse<BetFindAllResponses>> findAll(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "베팅 세부사항 조회", description = "특정 베팅의 세부사항을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "베팅 세부사항 조회 성공"),
		@ApiResponse(responseCode = "404", description = "해당 베팅을 찾을 수 없음")
	})
	ResponseEntity<RestResponse<BetFindResponse>> findBetDetails(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "베팅 생성", description = "새로운 베팅을 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "베팅 생성 성공"),
		@ApiResponse(responseCode = "400", description = "유효하지 않은 요청")
	})
	ResponseEntity<RestResponse<BetCreateResponse>> createBet(
		@PathVariable Long darakbangId,
		@RequestBody BetCreateRequest request,
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "베팅 참여", description = "기존 베팅에 참여한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "베팅 참여 성공"),
		@ApiResponse(responseCode = "404", description = "해당 베팅을 찾을 수 없음")
	})
	ResponseEntity<Void> participateBet(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "베팅 결과 조회", description = "특정 베팅의 결과를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "베팅 결과 조회 성공"),
		@ApiResponse(responseCode = "404", description = "해당 베팅 결과를 찾을 수 없음")
	})
	ResponseEntity<RestResponse<BetResultResponse>> findBetResult(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	);

	@Operation(summary = "베팅 결과 도출", description = "특정 베팅의 결과를 도출한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "베팅 결과 도출 성공"),
		@ApiResponse(responseCode = "404", description = "해당 베팅을 찾을 수 없음")
	})
	ResponseEntity<Void> drawBet(
		@PathVariable Long darakbangId,
		@PathVariable Long betId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	);
}
