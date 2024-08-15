package mouda.backend.darakbang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.darakbang.dto.request.DarakbangCreateRequest;
import mouda.backend.darakbang.dto.response.DarakbangResponses;
import mouda.backend.darakbang.dto.response.InvitationCodeResponse;
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

	@Operation(summary = "다락방 목록 조회", description = "참여한 다락방 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 목록 조회 성공!")
	})
	ResponseEntity<RestResponse<DarakbangResponses>> findAllMyDarakbangs(
		@LoginMember Member member
	);

	@Operation(summary = "다락방 참여코드 조회", description = "참여한 다락방 참여코드를 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 참여코드 조회 성공!"),
		@ApiResponse(responseCode = "403", description = "권한이 없습니다."),
	})
	ResponseEntity<RestResponse<InvitationCodeResponse>> findInvitationCode(
		@PathVariable Long darakbangId,
		@LoginMember Member member
	);
}
