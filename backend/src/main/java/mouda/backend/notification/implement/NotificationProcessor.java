package mouda.backend.notification.implement;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationSendEvent;

@Component
@RequiredArgsConstructor
public class NotificationProcessor {

	private final NotificationWriter notificationWriter;
	private final ApplicationEventPublisher notificationSendEventPublisher;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void process(NotificationPayload payload) {
		notificationWriter.saveMemberNotification(payload);

		NotificationSendEvent event = NotificationSendEvent.from(payload);
		notificationSendEventPublisher.publishEvent(event);
	}
}
