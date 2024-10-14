package mouda.backend.darakbangmember.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

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
import mouda.backend.member.presentation.response.DarakbangMemberInfoResponse;

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

	@Operation(summary = "마이페이지 조회", description = "마이페이지에 표시될 내 정보를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "마이페이지 조회 성공!")
	})
	ResponseEntity<RestResponse<DarakbangMemberInfoResponse>> findMyInfo(
		@LoginDarakbangMember DarakbangMember member
	);

	@Operation(summary = "마이페이지 수정", description = "마이페이지에 표시될 내 정보를 수정한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "마이페이지 수정 성공!")
	})
	ResponseEntity<Void> updateMyInfo(
		@LoginDarakbangMember DarakbangMember member,
		@RequestPart String isReset,
		@RequestPart MultipartFile file,
		@RequestPart String nickname,
		@RequestPart String description
	);
}
