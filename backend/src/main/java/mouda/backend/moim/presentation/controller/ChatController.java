package mouda.backend.moim.presentation.controller;

import java.util.List;

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
import mouda.backend.chat.business.ChatRoomService;
import mouda.backend.chat.business.ChatService;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.presentation.controller.swagger.ChatSwagger;
import mouda.backend.moim.presentation.request.chat.OldChatCreateRequest;
import mouda.backend.moim.presentation.request.chat.OldDateTimeConfirmRequest;
import mouda.backend.moim.presentation.request.chat.OldLastReadChatRequest;
import mouda.backend.moim.presentation.request.chat.OldPlaceConfirmRequest;
import mouda.backend.moim.presentation.response.chat.ChatPreviewResponse;
import mouda.backend.moim.presentation.response.chat.OldChatPreviewResponses;

@Deprecated
@RestController("oldChatController")
@RequestMapping("/v1/darakbang/{darakbangId}/chat")
@RequiredArgsConstructor
public class ChatController implements ChatSwagger {

	private final ChatService chatService;
	private final ChatRoomService chatRoomService;

	@Override
	@PostMapping
	public ResponseEntity<Void> createChat(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody OldChatCreateRequest oldChatCreateRequest
	) {
		ChatCreateRequest chatCreateRequest = new ChatCreateRequest(oldChatCreateRequest.content());
		chatService.createChat(darakbangId, oldChatCreateRequest.moimId(), chatCreateRequest, darakbangMember);

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
	@ExceptRequestLogging
	public ResponseEntity<RestResponse<OldChatPreviewResponses>> findChatPreviews(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		ChatPreviewResponses chatPreviewResponses = chatRoomService.findChatPreview(darakbangMember, ChatRoomType.MOIM);

		List<ChatPreviewResponse> previewResponses = chatPreviewResponses.previews().stream()
			.map(chatPreviewResponse -> new ChatPreviewResponse(chatPreviewResponse.chatRoomId(), chatPreviewResponse.title(), chatPreviewResponse.currentPeople(), chatPreviewResponse.isStarted(),
				chatPreviewResponse.lastContent(), chatPreviewResponse.lastReadChatId())).toList();
		OldChatPreviewResponses oldChatPreviewResponses = new OldChatPreviewResponses(previewResponses);
		return ResponseEntity.ok(new RestResponse<>(oldChatPreviewResponses));
	}

	@Override
	@PostMapping("/last")
	public ResponseEntity<Void> createLastReadChatId(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody OldLastReadChatRequest oldLastReadChatRequest
	) {
		LastReadChatRequest lastReadChatRequest = new LastReadChatRequest(oldLastReadChatRequest.lastReadChatId());
		chatService.updateLastReadChat(darakbangId, oldLastReadChatRequest.moimId(), lastReadChatRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/datetime")
	public ResponseEntity<Void> confirmDateTime(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody OldDateTimeConfirmRequest oldDateTimeConfirmRequest
	) {
		DateTimeConfirmRequest dateTimeConfirmRequest = new DateTimeConfirmRequest(oldDateTimeConfirmRequest.date(), oldDateTimeConfirmRequest.time());
		chatService.confirmDateTime(darakbangId, oldDateTimeConfirmRequest.moimId(), dateTimeConfirmRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/place")
	public ResponseEntity<Void> confirmPlace(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestBody OldPlaceConfirmRequest oldPlaceConfirmRequest
	) {
		PlaceConfirmRequest placeConfirmRequest = new PlaceConfirmRequest(oldPlaceConfirmRequest.place());
		chatService.confirmPlace(darakbangId, oldPlaceConfirmRequest.moimId(), placeConfirmRequest, darakbangMember);

		return ResponseEntity.ok().build();
	}

	@PatchMapping("/open")
	public ResponseEntity<Void> openChatRoom(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestParam("moimId") Long moimId
	) {
		chatRoomService.openChatRoom(darakbangId, moimId, darakbangMember);

		return ResponseEntity.ok().build();
	}
}
