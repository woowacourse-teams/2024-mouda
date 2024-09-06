package mouda.backend.api.please.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.api.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.dto.please.request.InterestUpdateRequest;

public interface InterestSwagger {

	@Operation(summary = "관심 상태 변경", description = "관심 상태를 변경한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "관심 상태 변경 성공!"),
	})
	ResponseEntity<Void> updateInterest(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody InterestUpdateRequest request
	);
}
