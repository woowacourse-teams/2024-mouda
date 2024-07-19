package mouda.backend.moim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.RestResponse;
import mouda.backend.moim.dto.request.MoimCreateRequest;
import mouda.backend.moim.dto.request.MoimJoinRequest;
import mouda.backend.moim.dto.response.MoimDetailsFindResponse;
import mouda.backend.moim.dto.response.MoimFindAllResponses;

public interface MoimSwagger {

	@Operation(summary = "모임 생성", description = "모임을 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 생성 성공!"),
	})
	ResponseEntity<RestResponse<Long>> createMoim(@RequestBody MoimCreateRequest moimCreateRequest);

	@Operation(summary = "모임 전체 조회", description = "모든 모임을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 조회 성공!"),
	})
	ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMoim();

	@Operation(summary = "모임 상세 조회", description = "모임 상세 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 상세 조회 성공!"),
	})
	ResponseEntity<RestResponse<MoimDetailsFindResponse>> findMoimDetails(@PathVariable Long moimId);

	@Operation(summary = "모임 참여", description = "모임에 참여한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 참여 성공!")
	})
	ResponseEntity<RestResponse<Void>> joinMoim(@RequestBody MoimJoinRequest moimJoinRequest);

	@Operation(summary = "모임 삭제", description = "해당하는 id의 모임을 삭제한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 삭제 성공!"),
	})
	ResponseEntity<Void> deleteMoim(@PathVariable Long moimId);
}
