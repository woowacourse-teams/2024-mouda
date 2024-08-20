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
		@LoginDarakbangMember DarakbangMember member,
		@Valid @RequestBody ChatCreateRequest chatCreateRequest
	) {
		chatService.createChat(darakbangId, chatCreateRequest, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping
	public ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam("recentChatId") Long recentChatId,
		@RequestParam("moimId") Long moimId
	) {
		ChatFindUnloadedResponse unloadedChats = chatService
			.findUnloadedChats(darakbangId, recentChatId, moimId, member);

		return ResponseEntity.ok(new RestResponse<>(unloadedChats));
	}

	@Override
	@GetMapping("/preview")
	public ResponseEntity<RestResponse<ChatPreviewResponses>> findChatPreviews(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member
	) {
		ChatPreviewResponses chatPreviewResponses = chatService.findChatPreview(darakbangId, member);

		return ResponseEntity.ok(new RestResponse<>(chatPreviewResponses));
	}

	@Override
	@PostMapping("/last")
	public ResponseEntity<Void> createLastReadChatId(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody LastReadChatRequest lastReadChatRequest
	) {
		chatService.createLastChat(darakbangId, lastReadChatRequest.moimId(), lastReadChatRequest, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/datetime")
	public ResponseEntity<Void> confirmDateTime(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody DateTimeConfirmRequest dateTimeConfirmRequest
	) {
		chatService.confirmDateTime(darakbangId, dateTimeConfirmRequest, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/place")
	public ResponseEntity<Void> confirmPlace(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestBody PlaceConfirmRequest placeConfirmRequest
	) {
		chatService.confirmPlace(darakbangId, placeConfirmRequest, member);

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/open")
	public ResponseEntity<Void> openChatRoom(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember member,
		@RequestParam("moimId") Long moimId
	) {
		chatService.openChatRoom(darakbangId, moimId, member);

		return ResponseEntity.ok().build();
	}
}
