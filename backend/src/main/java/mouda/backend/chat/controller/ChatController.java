package mouda.backend.chat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.service.ChatService;
import mouda.backend.common.RestResponse;
import mouda.backend.config.argumentresolver.LoginMember;
import mouda.backend.member.domain.Member;

@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@PostMapping
	public ResponseEntity<Void> createChat(
		@Valid @RequestBody ChatCreateRequest chatCreateRequest,
		@LoginMember Member member
	) {
		chatService.createChat(chatCreateRequest, member);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<RestResponse<ChatFindUnloadedResponse>> findUnloadedChats(
		@RequestParam("recentChatId") Long recentChatId,
		@RequestParam("moimId") Long moimId,
		@LoginMember Member member
	) {
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(recentChatId, moimId, member);
		return ResponseEntity.ok(new RestResponse<>(unloadedChats));
	}
}
