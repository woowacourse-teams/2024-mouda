package mouda.backend.member.controller;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;
import mouda.backend.member.dto.response.MemberFindResponse;

public interface MemberSwagger {

	@Operation(summary = "로그인 된 회원의 정보 조회", description = "로그인 된 회원의 정보를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공!"),
	})
	ResponseEntity<RestResponse<MemberFindResponse>> findMyInfo(@LoginMember Member member);
}
