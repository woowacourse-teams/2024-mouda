package mouda.backend.api.moim.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.api.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.api.common.response.RestResponse;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.dto.moim.request.chamyo.ChamyoCancelRequest;
import mouda.backend.core.dto.moim.request.chamyo.MoimChamyoRequest;
import mouda.backend.core.dto.moim.response.chamyo.ChamyoFindAllResponses;
import mouda.backend.core.dto.moim.response.chamyo.MoimRoleFindResponse;

public interface ChamyoSwagger {

	@Operation(summary = "모임 참여 여부 조회", description = "현재 로그인된 회원의 모임 참여 여부를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 참여 여부 조회 성공")
	})
	ResponseEntity<RestResponse<MoimRoleFindResponse>> findMoimRoleByMember(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam Long moimId
	);

	@Operation(summary = "모든 모임 참여자 조회", description = "모임에 참여한 모든 회원을 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모든 모임 참여자 조회 성공")
	})
	ResponseEntity<RestResponse<ChamyoFindAllResponses>> findAllChamyoByMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam Long moimId
	);

	@Operation(summary = "모임 참여", description = "모임에 참여합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 참여 성공")
	})
	ResponseEntity<Void> chamyoMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody MoimChamyoRequest request
	);

	@Operation(summary = "모임 참여 취소", description = "모임 참여를 취소합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 참여 취소 성공")
	})
	ResponseEntity<Void> cancelChamyo(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody ChamyoCancelRequest request);
}
