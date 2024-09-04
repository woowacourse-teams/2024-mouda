package mouda.backend.darakbang.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbang.presentation.request.DarakbangCreateRequest;
import mouda.backend.darakbang.presentation.request.DarakbangEnterRequest;
import mouda.backend.darakbang.presentation.response.CodeValidationResponse;
import mouda.backend.darakbang.presentation.response.DarakbangNameResponse;
import mouda.backend.darakbang.presentation.response.DarakbangResponses;
import mouda.backend.darakbang.presentation.response.InvitationCodeResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
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
		@ApiResponse(responseCode = "403", description = "조회 권한이 없습니다."),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 다락방 멤버입니다."),
		@ApiResponse(responseCode = "404", description = "다락방이 존재하지 않습니다."),
	})
	ResponseEntity<RestResponse<InvitationCodeResponse>> findInvitationCode(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);

	@Operation(summary = "다락방 초대코드 유효성 검사", description = "다락방 초대코드 유효성을 검사한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 초대코드 유효성 검사 성공!"),
		@ApiResponse(responseCode = "400", description = "유효하지 않은 초대코드입니다.")
	})
	ResponseEntity<RestResponse<CodeValidationResponse>> validateInvitationCode(
		@RequestParam String code
	);

	@Operation(summary = "다락방 참여", description = "다락방에 참여한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 참여 성공!"),
		@ApiResponse(responseCode = "400", description = "이미 존재하는 닉네임입니다."),
		@ApiResponse(responseCode = "400", description = "이미 가입한 멤버입니다."),
		@ApiResponse(responseCode = "404", description = "다락방이 존재하지 않습니다."),
	})
	ResponseEntity<RestResponse<Long>> enterDarakbang(
		@RequestParam String code,
		@RequestBody DarakbangEnterRequest darakbangEnterRequest,
		@LoginMember Member member
	);

	@Operation(summary = "다락방 이름 조회", description = "다락방 이름을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "다락방 이름 조회 성공!"),
		@ApiResponse(responseCode = "404", description = "다락방이 존재하지 않습니다.")
	})
	ResponseEntity<RestResponse<DarakbangNameResponse>> findDarakbangName(
		@PathVariable Long darakbangId
	);
}
