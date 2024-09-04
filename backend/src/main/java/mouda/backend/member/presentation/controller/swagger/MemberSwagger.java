package mouda.backend.member.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.presentation.response.MemberFindResponse;

public interface MemberSwagger {

	@Operation(summary = "로그인 된 회원의 정보 조회", description = "로그인 된 회원의 정보를 조회합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "조회 성공!"),
	})
	ResponseEntity<RestResponse<MemberFindResponse>> findMyInfo(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);
}
