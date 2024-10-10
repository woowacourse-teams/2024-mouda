package mouda.backend.notification.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.argumentresolver.LoginMember;
import mouda.backend.common.response.RestResponse;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.business.SubscriptionService;
import mouda.backend.notification.presentation.request.ChatSubscriptionRequest;
import mouda.backend.notification.presentation.response.SubscriptionResponse;

@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionSwagger {

	private final SubscriptionService subscriptionService;

	@GetMapping("/v1/subscription/moim")
	@Override
	public ResponseEntity<RestResponse<SubscriptionResponse>> readMoimCreateSubscription(
		@LoginMember Member member
	) {
		SubscriptionResponse response = subscriptionService.readMoimCreateSubscription(member);

		return ResponseEntity.ok(new RestResponse<>(response));
	}

	@PostMapping("/v1/subscription/moim")
	@Override
	public ResponseEntity<Void> changeMoimCreateSubscription(
		@LoginMember Member member
	) {
		subscriptionService.changeMoimCreateSubscription(member);

		return ResponseEntity.ok().build();
	}

	@GetMapping("/v1/subscription/chat")
	@Override
	public ResponseEntity<RestResponse<SubscriptionResponse>> readSpecificChatRoomSubscription(
		@LoginMember Member member,
		@Valid @RequestBody ChatSubscriptionRequest chatSubscriptionRequest
	) {
		SubscriptionResponse response = subscriptionService.readChatRoomSubscription(member,
			chatSubscriptionRequest);

		return ResponseEntity.ok(new RestResponse<>(response));
	}

	@PostMapping("/v1/subscription/chat")
	@Override
	public ResponseEntity<Void> changeChatRoomSubscription(
		@LoginMember Member member,
		@Valid @RequestBody ChatSubscriptionRequest chatSubscriptionRequest
	) {
		subscriptionService.changeChatRoomSubscription(member, chatSubscriptionRequest);

		return ResponseEntity.ok().build();
	}
}
