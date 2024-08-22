package mouda.backend.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.aop.logging.ExceptRequestLogging;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.DateTimeConfirmRequest;
import mouda.backend.chat.dto.request.LastReadChatRequest;
import mouda.backend.chat.dto.request.PlaceConfirmRequest;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.service.ChatService;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@RestController
@RequestMapping("/v1/darakbang/{darakbangId}/chat")
@RequiredArgsConstructor
public class ChatController implements ChatSwagger {

	private final ChatService chatService;

	@Override
	@PostMapping
	public ResponseEntity<Void> createChat(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody ChatCreateRequest chatCreateRequest
	) {
		chatService.createChat(darakbangId, chatCreateRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@Override
	@ExceptRequestLogging
	@GetMapping
	public ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestParam("recentChatId") Long recentChatId,
		@RequestParam("moimId") Long moimId
	) {
		ChatFindUnloadedResponse unloadedChats = chatService
			.findUnloadedChats(darakbangId, recentChatId, moimId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(unloadedChats));
	}

	@Override
	@GetMapping("/preview")
	public ResponseEntity<RestResponse<ChatPreviewResponses>> findChatPreviews(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		ChatPreviewResponses chatPreviewResponses = chatService.findChatPreview(darakbangId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(chatPreviewResponses));
	}

	@Override
	@PostMapping("/last")
	public ResponseEntity<Void> createLastReadChatId(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody LastReadChatRequest lastReadChatRequest
	) {
		chatService.createLastChat(darakbangId, lastReadChatRequest.moimId(), lastReadChatRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/datetime")
	public ResponseEntity<Void> confirmDateTime(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody DateTimeConfirmRequest dateTimeConfirmRequest
	) {
		chatService.confirmDateTime(darakbangId, dateTimeConfirmRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/place")
	public ResponseEntity<Void> confirmPlace(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody PlaceConfirmRequest placeConfirmRequest
	) {
		chatService.confirmPlace(darakbangId, placeConfirmRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/open")
	public ResponseEntity<Void> openChatRoom(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestParam("moimId") Long moimId
	) {
		chatService.openChatRoom(darakbangId, moimId, darakbangMember);

		return ResponseEntity.ok().build();
	}
}
