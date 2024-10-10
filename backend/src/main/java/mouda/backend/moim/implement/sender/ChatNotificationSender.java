package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.ChatRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
public class ChatNotificationSender extends AbstractMoimNotificationSender {

	private final ChatRecipientFinder chatRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public ChatNotificationSender(UrlConfig urlConfig, ChatRecipientFinder chatRecipientFinder, ApplicationEventPublisher eventPublisher) {
		super(urlConfig);
		this.chatRecipientFinder = chatRecipientFinder;
		this.eventPublisher = eventPublisher;
	}

	public void sendChatNotification(Moim moim, DarakbangMember sender, NotificationType notificationType) {
		List<Recipient> recipients = chatRecipientFinder.getChatNotificationRecipients(moim.getId(), sender);
		NotificationEvent notificationEvent = createNotificationEvent(moim, sender, notificationType, recipients);

		eventPublisher.publishEvent(notificationEvent);
	}

	private NotificationEvent createNotificationEvent(Moim moim, DarakbangMember sender, NotificationType notificationType, List<Recipient> recipients) {
		String message;
		if (notificationType.isConfirmedType()) {
			message = notificationType.createMessage(moim.getTitle());
		} else {
			message = notificationType.createMessage(sender.getNickname());
		}

		return new NotificationEvent(
			notificationType, moim.getTitle(), message, getChatRoomUrl(moim.getDarakbangId(), moim.getId()), recipients, moim.getDarakbangId(), moim.getId());
	}
}
