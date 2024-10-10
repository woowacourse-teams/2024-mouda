package mouda.backend.notification.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.domain.Subscription;
import mouda.backend.notification.implement.subscription.SubscriptionFinder;
import mouda.backend.notification.implement.subscription.SubscriptionWriter;
import mouda.backend.notification.presentation.request.ChatSubscriptionRequest;
import mouda.backend.notification.presentation.response.SubscriptionResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

	private final SubscriptionFinder subscriptionFinder;
	private final SubscriptionWriter subscriptionWriter;

	public SubscriptionResponse readMoimCreateSubscription(Member member) {
		Subscription subscription = subscriptionFinder.readSubscription(member);
		boolean isSubscribed = subscription.isSubscribedMoimCreate();

		return SubscriptionResponse.builder()
			.isSubscribed(isSubscribed)
			.build();
	}

	public SubscriptionResponse readChatRoomSubscription(
		Member member, ChatSubscriptionRequest request
	) {
		Subscription subscription = subscriptionFinder.readSubscription(member);
		boolean isSubscribed = subscription.isSubscribedChatRoom(request.darakbangId(), request.chatRoomId());

		return SubscriptionResponse.builder()
			.isSubscribed(isSubscribed)
			.build();
	}

	public void changeMoimCreateSubscription(Member member) {
		subscriptionWriter.changeMoimSubscription(member);
	}

	public void changeChatRoomSubscription(Member member, ChatSubscriptionRequest request) {
		subscriptionWriter.changeChatRoomSubscription(member, request.darakbangId(), request.chatRoomId());
	}
}
