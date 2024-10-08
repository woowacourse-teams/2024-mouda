package mouda.backend.notification.business;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.implement.NotificationSender;
import mouda.backend.notification.implement.NotificationWriter;
import mouda.backend.notification.implement.filter.SubscriptionFilter;
import mouda.backend.notification.implement.filter.SubscriptionFilterRegistry;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationWriter notificationWriter;
	private final SubscriptionFilterRegistry subscriptionFilterRegistry;
	private final NotificationSender notificationSender;

	@EventListener
	public void sendNotification(NotificationEvent notificationEvent) {
		CommonNotification commonNotification = notificationEvent.toCommonNotification();
		notificationWriter.saveAllMemberNotification(commonNotification, notificationEvent.getRecipients());

		SubscriptionFilter subscriptionFilter = subscriptionFilterRegistry.getFilter(notificationEvent.getNotificationType());
		List<Recipient> filteredRecipients = subscriptionFilter.filter(notificationEvent);

		notificationSender.sendNotification(commonNotification, filteredRecipients);
	}
}

