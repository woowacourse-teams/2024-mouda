package mouda.backend.notification.implement;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationSendEvent;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.implement.filter.SubscriptionFilterRegistry;

@Component
@RequiredArgsConstructor
public class NotificationSendEventHandler {

	private final SubscriptionFilterRegistry subscriptionFilterRegistry;
	private final NotificationSender notificationSender;

	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = NotificationSendEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(NotificationSendEvent event) {
		List<Recipient> filteredRecipients = filterRecipientsBySubscription(event);
		notificationSender.sendNotification(event.getNotification(), filteredRecipients);
	}

	private List<Recipient> filterRecipientsBySubscription(NotificationSendEvent event) {
		return subscriptionFilterRegistry.getFilter(event.getNotification().getType()).filter(event);
	}
}
