package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.implement.finder.ChamyoRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class ChamyoNotificationSender {

	private final ChamyoRecipientFinder chamyoRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public void sendChamyoNotification(long moimId, DarakbangMember updatedMember, NotificationType notificationType) {
		List<Recipient> recipients = chamyoRecipientFinder.getChamyoNotificationRecipients(moimId, updatedMember);
		NotificationEvent notificationEvent = new NotificationEvent(notificationType, updatedMember.getDarakbang().getName(), notificationType.createMessage(updatedMember.getNickname()), recipients);

		eventPublisher.publishEvent(notificationEvent);
	}
}
