package mouda.backend.moim.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.FilterType;
import mouda.backend.moim.presentation.request.comment.CommentCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimCreateRequest;
import mouda.backend.moim.presentation.request.moim.MoimEditRequest;
import mouda.backend.moim.presentation.response.moim.MoimDetailsFindResponse;
import mouda.backend.moim.presentation.response.moim.MoimFindAllResponses;

public interface MoimSwagger {

	@Operation(summary = "모임 생성", description = "모임을 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 생성 성공!"),
	})
	ResponseEntity<RestResponse<Long>> createMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody MoimCreateRequest moimCreateRequest
	);

	@Operation(summary = "모임 전체 조회", description = "모든 모임을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 조회 성공!"),
	})
	ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);

	@Operation(summary = "모임 상세 조회", description = "모임 상세 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 상세 조회 성공!"),
	})
	ResponseEntity<RestResponse<MoimDetailsFindResponse>> findMoimDetails(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	);

	@Operation(summary = "모임 삭제", description = "해당하는 id의 모임을 삭제한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 삭제 성공!"),
	})
	ResponseEntity<Void> deleteMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	);

	@Operation(summary = "모집 완료", description = "방장이 모집을 완료합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모집 완료 성공!")
	})
	ResponseEntity<Void> completeMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	);

	@Operation(summary = "모임 취소", description = "방장이 모임을 취소합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 취소 성공!")
	})
	ResponseEntity<Void> cancelMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	);

	@Operation(summary = "모집 재개", description = "방장이 모집을 재개합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모집 재개 성공!")
	})
	ResponseEntity<Void> reopenMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId
	);

	@Operation(summary = "모임 수정", description = "해당하는 id의 모임을 수정한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "모임 수정 성공!")
	})
	ResponseEntity<Void> editMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody MoimEditRequest request
	);

	@Operation(summary = "댓글 작성", description = "해당하는 id의 모임에 댓글을 생성한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "댓글 생성 성공!")
	})
	ResponseEntity<Void> createComment(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@PathVariable Long moimId,
		@RequestBody CommentCreateRequest commentCreateRequest);

	@Operation(summary = "나의 모임 목록 조회", description = "내가 참여하는 모임의 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "나의 모임 목록 조회 성공!")
	})
	ResponseEntity<RestResponse<MoimFindAllResponses>> findAllMyMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam(value = "filter", defaultValue = "ALL") FilterType filter
	);

	@Operation(summary = "찜한 모임 목록 조회", description = "찜한 모임의 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "찜한 모임 조회 성공!")
	})
	ResponseEntity<RestResponse<MoimFindAllResponses>> findAllZzimedMoim(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);
}
