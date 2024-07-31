package mouda.backend.chamyo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.chamyo.dto.ChamyoFindAllResponses;
import mouda.backend.chamyo.dto.MoimRoleFindResponse;
import mouda.backend.common.RestResponse;
import mouda.backend.member.domain.Member;

public interface ChamyoSwagger {

	@Operation(summary = "모임 참여 여부 조회", description = "현재 로그인된 회원의 모임 참여 여부를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 참여 여부 조회 성공")
	})
	ResponseEntity<RestResponse<MoimRoleFindResponse>> findMoimRoleByMember(@RequestParam Long moimId, Member member);

	@Operation(summary = "모든 모임 참여자 조회", description = "모임에 참여한 모든 회원을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모든 모임 참여자 조회 성공")
	})
	ResponseEntity<RestResponse<ChamyoFindAllResponses>> findAllChamyo(@RequestParam Long moimId);
}
