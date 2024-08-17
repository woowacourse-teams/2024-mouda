package mouda.backend.please.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.please.dto.request.InterestUpdateRequest;

public interface InterestSwagger {

	@Operation(summary = "관심 상태 변경", description = "관심 상태를 변경한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "관심 상태 변경 성공!"),
	})
	ResponseEntity<Void> updateInterest(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@RequestBody InterestUpdateRequest request
	);
}
