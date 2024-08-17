package mouda.backend.zzim.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.zzim.dto.request.ZzimUpdateRequest;
import mouda.backend.zzim.dto.response.ZzimCheckResponse;

public interface ZzimSwagger {

	@Operation(summary = "찜 여부 조회", description = "해당 모임에 대한 회원의 찜 여부를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "찜 여부 조회 성공!")
	})
	ResponseEntity<RestResponse<ZzimCheckResponse>> checkZzimByMoimAndMember(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@RequestParam Long moimId
	);

	@Operation(summary = "모임 찜하기", description = "해당 모임을 찜한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 찜하기 성공!")
	})
	ResponseEntity<Void> updateZzim(
		@PathVariable Long darakbangId,
		@LoginMember Member member,
		@Valid @RequestBody ZzimUpdateRequest request
	);
}
