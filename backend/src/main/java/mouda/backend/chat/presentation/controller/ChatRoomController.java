package mouda.backend.chat.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.business.ChatRoomService;
import mouda.backend.chat.presentation.response.ChatRoomDetailsResponse;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/darakbang/{darakbangId}/chatroom/{chatRoomId}")
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping("/details")
	public ResponseEntity<RestResponse<ChatRoomDetailsResponse>> findChatRoomDetails(
		@PathVariable Long darakbangId,
		@PathVariable Long chatRoomId,
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		ChatRoomDetailsResponse chatRoomDetails = chatRoomService.findChatRoomDetails(darakbangId, chatRoomId, darakbangMember);

		return ResponseEntity.ok(new RestResponse<>(chatRoomDetails));
	}
}
