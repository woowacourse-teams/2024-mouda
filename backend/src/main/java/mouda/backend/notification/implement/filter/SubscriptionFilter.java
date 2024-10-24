package mouda.backend.notification.implement.filter;

import java.util.List;

import mouda.backend.notification.domain.NotificationSendEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

public interface SubscriptionFilter {

	boolean support(NotificationType notificationType);

	List<Recipient> filter(NotificationSendEvent notificationSendEvent);
}
