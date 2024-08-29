package mouda.backend.zzim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.exception.ErrorResponse;
import mouda.backend.zzim.dto.request.ZzimUpdateRequest;
import mouda.backend.zzim.dto.response.ZzimCheckResponse;

public interface ZzimSwagger {

	@Operation(summary = "찜 여부 조회", description = "해당 모임에 대한 회원의 찜 여부를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "찜 여부 조회 성공!"),
		@ApiResponse(responseCode = "404", description = "모임이 존재하지 않거나, 모임은 존재하지만 입력된 다락방의 모임이 아닌 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	ResponseEntity<RestResponse<ZzimCheckResponse>> checkZzimByMoimAndMember(
		@PathVariable @Parameter(in = ParameterIn.PATH, description = "다락방 ID", required = true) Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam @Parameter(in = ParameterIn.QUERY, description = "모임 ID", required = true) Long moimId
	);

	@Operation(summary = "모임 찜하기", description = "해당 모임을 찜한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 찜하기 성공!"),
		@ApiResponse(responseCode = "404", description = "모임이 존재하지 않거나, 모임은 존재하지만 입력된 다락방의 모임이 아닌 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	ResponseEntity<Void> updateZzim(
		@PathVariable @Parameter(in = ParameterIn.PATH, description = "다락방 ID", required = true) Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody ZzimUpdateRequest request
	);
}
