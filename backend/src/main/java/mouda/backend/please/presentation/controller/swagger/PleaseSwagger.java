package mouda.backend.please.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.presentation.request.PleaseCreateRequest;
import mouda.backend.please.presentation.response.PleaseFindAllResponses;

public interface PleaseSwagger {

	@Operation(summary = "해주세요 생성", description = "해주세요를 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "해주세요 생성 성공!"),
	})
	ResponseEntity<RestResponse<Long>> createPlease(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody PleaseCreateRequest pleaseCreateRequest
	);

	@Operation(summary = "해주세요 삭제", description = "해주세요를 삭제한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "해주세요 삭제 성공!"),
	})
	ResponseEntity<Void> deletePlease(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long pleaseId
	);

	@Operation(summary = "해주세요 목록 조회", description = "해주세요 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "해주세요 목록 조회 성공!"),
	})
	ResponseEntity<RestResponse<PleaseFindAllResponses>> findAllPlease(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);
}
