package mouda.backend.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.DateTimeConfirmRequest;
import mouda.backend.chat.dto.request.PlaceConfirmRequest;
import mouda.backend.chat.dto.request.LastReadChatRequest;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.service.ChatService;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController implements ChatSwagger {

	private final ChatService chatService;

	@Override
	@PostMapping
	public ResponseEntity<Void> createChat(
		@Valid @RequestBody ChatCreateRequest chatCreateRequest,
		@LoginMember Member member
	) {
		chatService.createChat(chatCreateRequest, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@GetMapping
	public ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@RequestParam("recentChatId") Long recentChatId,
		@RequestParam("moimId") Long moimId,
		@LoginMember Member member
	) {
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(recentChatId, moimId, member);

		return ResponseEntity.ok(new RestResponse<>(unloadedChats));
	}
  
  @Override  
  @GetMapping("/preview")
	public ResponseEntity<RestResponse<ChatPreviewResponses>> findChatPreviews(
		@LoginMember Member member
	) {
		ChatPreviewResponses chatPreviewResponses = chatService.findChatPreview(member);

		return ResponseEntity.ok(new RestResponse<>(chatPreviewResponses));
	}

	@Override
	@PostMapping("/last")
	public ResponseEntity<Void> createLastReadChatId(
		@RequestBody LastReadChatRequest lastReadChatRequest,
		@LoginMember Member member
	) {
		chatService.createLastChat(lastReadChatRequest, member);

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/datetime")
	public ResponseEntity<Void> confirmDateTime(
		@RequestBody DateTimeConfirmRequest dateTimeConfirmRequest,
		@LoginMember Member member
	) {
		chatService.confirmDateTime(dateTimeConfirmRequest, member);
    
		return ResponseEntity.ok().build();
	}
  
  @Override
	@PostMapping("/place")
	public ResponseEntity<Void> confirmPlace(
		@RequestBody PlaceConfirmRequest placeConfirmRequest,
		@LoginMember Member member
	) {
		chatService.confirmPlace(placeConfirmRequest, member);
    
    return ResponseEntity.ok().build();
  }
    
  @PatchMapping("/open")
	public ResponseEntity<Void> openChatRoom(
		@RequestParam("moimId") Long moimId,
		@LoginMember Member member
	) {
		chatService.openChatRoom(moimId, member);
    
    return ResponseEntity.ok().build();
  }
}
