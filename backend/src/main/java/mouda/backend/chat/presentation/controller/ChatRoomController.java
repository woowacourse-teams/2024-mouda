package mouda.backend.chat.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.aop.logging.ExceptRequestLogging;
import mouda.backend.chat.business.ChatRoomService;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.chat.presentation.response.ChatRoomDetailsResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/darakbang/{darakbangId}/chatroom")
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping("/{chatRoomId}/details")
	public ResponseEntity<RestResponse<ChatRoomDetailsResponse>> findChatRoomDetails(
		@PathVariable Long darakbangId,
		@PathVariable Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		ChatRoomDetailsResponse chatRoomDetails = chatRoomService.findChatRoomDetails(darakbangId, chatRoomId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(chatRoomDetails));
	}

	@GetMapping("/preview")
	@ExceptRequestLogging
	public ResponseEntity<RestResponse<ChatPreviewResponses>> findChatPreviews(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestParam("chatRoomType") ChatRoomType chatRoomType
	) {
		ChatPreviewResponses chatPreviewResponses = chatRoomService.findChatPreview(darakbangMember, chatRoomType);

		return ResponseEntity.ok(new RestResponse<>(chatPreviewResponses));
	}

	@PatchMapping("/open")
	public ResponseEntity<RestResponse<Long>> openChatRoom(
		@PathVariable Long darakbangId,
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@RequestParam("moimId") Long moimId
	) {
		long chatRoomId = chatRoomService.openChatRoom(darakbangId, moimId, darakbangMember);

		return ResponseEntity.ok().body(new RestResponse<>(chatRoomId));
	}
}
