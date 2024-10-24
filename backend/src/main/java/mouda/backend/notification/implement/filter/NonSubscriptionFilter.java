package mouda.backend.notification.implement.filter;

import java.util.List;

import org.springframework.stereotype.Component;

import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
public class NonSubscriptionFilter implements SubscriptionFilter {

	@Override
	public boolean support(NotificationType notificationType) {
		return notificationType != NotificationType.NEW_CHAT &&
			!notificationType.isConfirmedType() &&
			notificationType != NotificationType.MOIM_CREATED;
	}

	@Override
	public List<Recipient> filter(NotificationEvent notificationEvent) {
		return notificationEvent.getRecipients();
	}
}
