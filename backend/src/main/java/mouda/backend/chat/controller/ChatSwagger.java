package mouda.backend.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.LastReadChatRequest;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;

public interface ChatSwagger {

	@Operation(summary = "채팅 생성", description = "한 건의 채팅 메시지를 보낸다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅 생성 성공!")
	})
	ResponseEntity<Void> createChat(
		@RequestBody ChatCreateRequest chatCreateRequest,
		@LoginMember Member member
	);

	@Operation(summary = "채팅 조회", description = "아직 조회되지 않은 채팅 내역을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅 조회 성공!")
	})
	ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@RequestParam Long recentChatId,
		@RequestParam Long moimId,
		@LoginMember Member member
	);

	@Operation(summary = "채팅방 목록 조회", description = "채팅방 목록을 조회한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅방 조회 성공!")
	})
	ResponseEntity<RestResponse<ChatPreviewResponses>> findChatPreviews(
		@LoginMember Member member
	);

	@Operation(summary = "마지막 읽은 채팅 저장", description = "마지막 읽은 채팅을 저장한다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "마지막 채팅 저장 성공!")
	})
	ResponseEntity<Void> createLastReadChatId(
		@RequestBody LastReadChatRequest lastReadChatRequest,
		@LoginMember Member member
	);

	@Operation(summary = "채팅방 열기", description = "채팅방을 연다!")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "채팅방 열기 성공!")
	})
	ResponseEntity<Void> openChatRoom(
		@RequestParam("moimId") Long moimId,
		@LoginMember Member member
	);
}
