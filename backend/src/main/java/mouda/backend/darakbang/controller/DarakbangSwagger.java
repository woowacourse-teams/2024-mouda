package mouda.backend.darakbang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
import mouda.backend.member.domain.Member;

public interface DarakbangSwagger {

	@Operation(summary = "다락방 생성", description = "다락방을 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 생성 성공!"),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 다락방 이름입니다.")
	})
	ResponseEntity<RestResponse<Long>> createDarakbang(
		@RequestBody DarakbangCreateRequest darakbangCreateRequest,
		@LoginMember Member member
	);
}
