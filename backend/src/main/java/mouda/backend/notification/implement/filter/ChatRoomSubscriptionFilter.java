package mouda.backend.notification.implement.filter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationSendEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.domain.Subscription;
import mouda.backend.notification.implement.subscription.SubscriptionFinder;

@Component
@RequiredArgsConstructor
public class ChatRoomSubscriptionFilter implements SubscriptionFilter {

	private final SubscriptionFinder subscriptionFinder;

	@Override
	public boolean support(NotificationType notificationType) {
		return notificationType == NotificationType.NEW_CHAT ||
			notificationType.isConfirmedType();
	}

	@Override
	public List<Recipient> filter(NotificationSendEvent notificationSendEvent) {
		return notificationSendEvent.getRecipients().stream()
			.filter(recipient -> {
				NotificationType type = notificationSendEvent.getNotification().getType();
				if (type.isConfirmedType()) {
					return true;
				}
				Subscription subscription = subscriptionFinder.readSubscription(recipient.getMemberId());
				return subscription.isSubscribedChatRoom(notificationSendEvent.getDarakbangId(), notificationSendEvent.getChatRoomId());
			})
			.toList();
	}
}
