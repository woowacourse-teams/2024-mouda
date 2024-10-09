package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginDarakbangMember;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.business.SubscriptionService;
import mouda.backend.notification.presentation.request.ChatSubscriptionRequest;
import mouda.backend.notification.presentation.response.SubscriptionResponse;

@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionSwagger {

	private final SubscriptionService subscriptionService;

	@GetMapping("/v1/subscription/moim")
	@Override
	public ResponseEntity<SubscriptionResponse> readMoimCreateSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		SubscriptionResponse response = subscriptionService.readMoimCreateSubscription(darakbangMember);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/v1/subscription/moim")
	@Override
	public ResponseEntity<Void> changeMoimCreateSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember
	) {
		subscriptionService.changeMoimCreateSubscription(darakbangMember);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/v1/subscription/chat")
	@Override
	public ResponseEntity<SubscriptionResponse> readSpecificChatRoomSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody ChatSubscriptionRequest chatSubscriptionRequest
	) {
		SubscriptionResponse response = subscriptionService.readChatRoomSubscription(darakbangMember,
			chatSubscriptionRequest);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/v1/subscription/chat")
	@Override
	public ResponseEntity<Void> changeChatRoomSubscription(
		@LoginDarakbangMember DarakbangMember darakbangMember,
		@Valid @RequestBody ChatSubscriptionRequest chatSubscriptionRequest
	) {
		subscriptionService.changeChatRoomSubscription(darakbangMember, chatSubscriptionRequest);

		return ResponseEntity.ok().build();
	}
}
