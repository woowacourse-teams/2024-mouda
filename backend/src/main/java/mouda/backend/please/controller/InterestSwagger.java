package mouda.backend.please.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.exception.ErrorResponse;
import mouda.backend.please.dto.request.InterestUpdateRequest;

public interface InterestSwagger {

	@Operation(summary = "관심 상태 변경", description = "관심 상태를 변경한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "관심 상태 변경 성공!"),
		@ApiResponse(responseCode = "404", description = "해주세요가 존재하지 않거나, 존재하지만 입력된 다락방의 해주세요가 아닌 경우",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	ResponseEntity<Void> updateInterest(
		@PathVariable @Parameter(in = ParameterIn.PATH, description = "다락방 ID", required = true) Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody InterestUpdateRequest request
	);
}
