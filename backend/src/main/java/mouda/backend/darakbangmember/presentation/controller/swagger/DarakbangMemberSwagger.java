package mouda.backend.darakbangmember.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberResponses;
import mouda.backend.darakbangmember.presentation.response.DarakbangMemberRoleResponse;
import mouda.backend.member.domain.Member;

public interface DarakbangMemberSwagger {

	@Operation(summary = "다락방 멤버 목록 조회", description = "다락방 멤버 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 멤버 목록 조회 성공!"),
		@ApiResponse(responseCode = "403", description = "존재하지 않는 다락방 멤버입니다."),
		@ApiResponse(responseCode = "403", description = "조회 권한이 없습니다.")
	})
	ResponseEntity<RestResponse<DarakbangMemberResponses>> findAllDarakbangMembers(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);

	@Operation(summary = "다락방 멤버 권한 조회", description = "다락방 멤버 권한을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 멤버 권한 조회 성공!")
	})
	ResponseEntity<RestResponse<DarakbangMemberRoleResponse>> findDarakbangMemberRole(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	);
}
