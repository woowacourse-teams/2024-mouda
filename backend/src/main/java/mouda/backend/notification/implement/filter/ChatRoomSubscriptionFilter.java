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
public class ChatRoomSubscriptionFilter implements SubscriptionFilter {

	private final SubscriptionFinder subscriptionFinder;

	@Override
	public boolean support(NotificationType notificationType) {
		return notificationType == NotificationType.NEW_CHAT ||
			notificationType.isConfirmedType();
	}

	@Override
	public List<Recipient> filter(NotificationEvent notificationEvent) {
		return notificationEvent.getRecipients().stream()
			.filter(recipient -> {
				// todo: 장소(시간) 확정 채팅은 알림이 가야함.
				if (notificationEvent.getNotificationType().isConfirmedType()) {
					return true;
				}
				Subscription subscription = subscriptionFinder.readSubscription(recipient.getMemberId());
				return subscription.isSubscribedChatRoom(notificationEvent.getDarakbangId(), notificationEvent.getChatRoomId());
			})
			.toList();
	}
}
