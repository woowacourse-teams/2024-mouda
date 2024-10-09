package mouda.backend.notification.business;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.Subscription;
import mouda.backend.notification.implement.subscription.SubscriptionFinder;
import mouda.backend.notification.implement.subscription.SubscriptionWriter;
import mouda.backend.notification.presentation.request.ChatSubscriptionRequest;
import mouda.backend.notification.presentation.response.SubscriptionResponse;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

	private final SubscriptionFinder subscriptionFinder;
	private final SubscriptionWriter subscriptionWriter;

	public SubscriptionResponse readMoimCreateSubscription(DarakbangMember darakbangMember) {
		Subscription subscription = subscriptionFinder.readSubscription(darakbangMember);
		boolean isSubscribed = subscription.isSubscribedMoimCreate();

		return SubscriptionResponse.builder()
			.isSubscribed(isSubscribed)
			.build();
	}

	public SubscriptionResponse readChatRoomSubscription(
		DarakbangMember darakbangMember, ChatSubscriptionRequest request
	) {
		Subscription subscription = subscriptionFinder.readSubscription(darakbangMember);
		boolean isSubscribed = subscription.isSubscribedChatRoom(request.darakbangId(), request.chatRoomId());

		return SubscriptionResponse.builder()
			.isSubscribed(isSubscribed)
			.build();
	}

	public void changeMoimCreateSubscription(DarakbangMember darakbangMember) {
		subscriptionWriter.changeMoimSubscription(darakbangMember);
	}

	public void changeChatRoomSubscription(DarakbangMember darakbangMember, ChatSubscriptionRequest request) {
		subscriptionWriter.changeChatRoomSubscription(darakbangMember, request.darakbangId(), request.chatRoomId());
	}
}
