package mouda.backend.notification.implement;

import java.util.List;

import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.Recipient;

public interface NotificationSender {

	void sendNotification(CommonNotification notification, List<Recipient> recipients);
}
