package mouda.backend.notification.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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

	@TransactionalEventListener(classes = NotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sendNotification(NotificationEvent notificationEvent) {
		CommonNotification commonNotification = notificationEvent.toCommonNotification();
		notificationWriter.saveAllMemberNotification(commonNotification, notificationEvent.getRecipients());

		SubscriptionFilter subscriptionFilter = subscriptionFilterRegistry.getFilter(notificationEvent.getNotificationType());
		List<Recipient> filteredRecipients = subscriptionFilter.filter(notificationEvent);

		notificationSender.sendNotification(commonNotification, filteredRecipients);
	}
}
