package mouda.backend.notification.implement.filter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.domain.Subscription;
import mouda.backend.notification.implement.subscription.SubscriptionFinder;

@Component
@RequiredArgsConstructor
public class MoimCreatedSubscriptionFilter implements SubscriptionFilter {

	private final SubscriptionFinder subscriptionFinder;

	@Override
	public boolean support(NotificationType notificationType) {
		return notificationType == NotificationType.MOIM_CREATED;
	}

	@Override
	public List<Recipient> filter(NotificationEvent notificationEvent) {
		return notificationEvent.getRecipients().stream()
			.filter(recipient -> {
				Subscription subscription = subscriptionFinder.readSubscription(recipient.getMemberId());
				return subscription.isSubscribedMoimCreate();
			})
			.toList();
	}
}
