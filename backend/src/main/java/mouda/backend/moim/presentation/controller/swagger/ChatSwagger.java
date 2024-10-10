package mouda.backend.moim.presentation.controller.swagger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.presentation.request.chat.OldChatCreateRequest;
import mouda.backend.moim.presentation.request.chat.OldDateTimeConfirmRequest;
import mouda.backend.moim.presentation.request.chat.OldLastReadChatRequest;
import mouda.backend.moim.presentation.request.chat.OldPlaceConfirmRequest;
import mouda.backend.moim.presentation.response.chat.OldChatPreviewResponses;

public interface ChatSwagger {

	@Operation(summary = "채팅 생성", description = "한 건의 채팅 메시지를 보낸다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅 생성 성공!")
	})
	ResponseEntity<Void> createChat(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody OldChatCreateRequest oldChatCreateRequest
	);

	@Operation(summary = "채팅 조회", description = "아직 조회되지 않은 채팅 내역을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅 조회 성공!")
	})
	ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam Long recentChatId,
		@RequestParam Long moimId
	);

	@Operation(summary = "장소 확정", description = "작성자가 장소를 확정하는 채팅을 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "장소 확정 성공!")
	})
	ResponseEntity<Void> confirmPlace(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody OldPlaceConfirmRequest oldPlaceConfirmRequest
	);

	@Operation(summary = "날짜 시간 확정", description = "작성자가 날짜와 시간을 확정하는 채팅을 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "날짜 시간 확정 성공!")
	})
	ResponseEntity<Void> confirmDateTime(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody OldDateTimeConfirmRequest oldDateTimeConfirmRequest
	);

	@Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅방 조회 성공!")
	})
	ResponseEntity<RestResponse<OldChatPreviewResponses>> findChatPreviews(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	);

	@Operation(summary = "마지막 읽은 채팅 저장", description = "마지막 읽은 채팅을 저장한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "마지막 채팅 저장 성공!")
	})
	ResponseEntity<Void> createLastReadChatId(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody OldLastReadChatRequest oldLastReadChatRequest
	);

	@Operation(summary = "채팅방 열기", description = "채팅방을 연다!")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅방 열기 성공!")
	})
	ResponseEntity<Void> openChatRoom(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam("moimId") Long moimId
	);
}
