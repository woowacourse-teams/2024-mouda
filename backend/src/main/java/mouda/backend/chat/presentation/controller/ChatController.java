package mouda.backend.chat.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import mouda.backend.aop.logging.ExceptRequestLogging;
import mouda.backend.chat.business.ChatService;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Tag(name = "신 채팅 API")
@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/chatroom")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	// @override
	@PostMapping("/{chatRoomId}")
	public ResponseEntity<Void> createChat(
		@PathVariable Long darakbangId,
		@PathVariable Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody ChatCreateRequest chatCreateRequest
	) {
		chatService.createChat(darakbangId, chatRoomId, chatCreateRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	// @override
	@ExceptRequestLogging
	@GetMapping("/{chatRoomId}")
	public ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@PathVariable Long darakbangId,
		@PathVariable @Positive Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestParam("recentChatId") Long recentChatId
	) {
		ChatFindUnloadedResponse unloadedChats = chatService
			.findUnloadedChats(darakbangId, recentChatId, chatRoomId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(unloadedChats));
	}

	// @override
	@PostMapping("/{chatRoomId}/last")
	public ResponseEntity<Void> createLastReadChatId(
		@PathVariable Long darakbangId,
		@PathVariable Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody LastReadChatRequest lastReadChatRequest
	) {
		chatService.updateLastReadChat(darakbangId, chatRoomId, lastReadChatRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	// @override
	@PostMapping("/{chatRoomId}/datetime")
	public ResponseEntity<Void> confirmDateTime(
		@PathVariable Long darakbangId,
		@PathVariable Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody DateTimeConfirmRequest dateTimeConfirmRequest
	) {
		chatService.confirmDateTime(darakbangId, chatRoomId, dateTimeConfirmRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	// @override
	@PostMapping("/{chatRoomId}/place")
	public ResponseEntity<Void> confirmPlace(
		@PathVariable Long darakbangId,
		@PathVariable Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody PlaceConfirmRequest placeConfirmRequest
	) {
		chatService.confirmPlace(darakbangId, chatRoomId, placeConfirmRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}
}
