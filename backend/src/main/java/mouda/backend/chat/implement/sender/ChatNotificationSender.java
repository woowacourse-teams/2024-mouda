package mouda.backend.chat.implement.sender;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.ChatRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
@EnableConfigurationProperties(UrlConfig.class)
@RequiredArgsConstructor
public class ChatNotificationSender {

	private final UrlConfig urlConfig;
	private final ChatRecipientFinder chatRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public void sendChatNotification(Moim moim, DarakbangMember sender, NotificationType notificationType, long chatRoomId) {
		List<Recipient> recipients = chatRecipientFinder.getChatNotificationRecipients(moim.getId(), sender);
		NotificationEvent notificationEvent = createNotificationEvent(moim, sender, notificationType, recipients, chatRoomId);

		eventPublisher.publishEvent(notificationEvent);
	}

	private NotificationEvent createNotificationEvent(Moim moim, DarakbangMember sender, NotificationType notificationType, List<Recipient> recipients, long chatRoomId) {
		String message;
		if (notificationType.isConfirmedType()) {
			message = notificationType.createMessage(moim.getTitle());
		} else {
			message = notificationType.createMessage(sender.getNickname());
		}

		return new NotificationEvent(
			notificationType, moim.getTitle(), message, urlConfig.getChatRoomUrl(moim.getDarakbangId(), chatRoomId), recipients, moim.getDarakbangId(), chatRoomId);
	}
}
