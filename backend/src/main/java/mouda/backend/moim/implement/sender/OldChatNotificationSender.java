package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.ChatRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Component
public class OldChatNotificationSender extends AbstractMoimNotificationSender {

	private final ChatRecipientFinder chatRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public OldChatNotificationSender(UrlConfig urlConfig, ChatRecipientFinder chatRecipientFinder,
		ApplicationEventPublisher eventPublisher) {
		super(urlConfig);
		this.chatRecipientFinder = chatRecipientFinder;
		this.eventPublisher = eventPublisher;
	}

	public void sendChatNotification(Chat chat, DarakbangMember sender, NotificationType notificationType) {
		Moim moim = chat.getMoim();
		List<Recipient> recipients = chatRecipientFinder.getMoimChatNotificationRecipients(moim.getId(), sender);
		NotificationEvent notificationEvent = createNotificationEvent(chat, notificationType, recipients);

		eventPublisher.publishEvent(notificationEvent);
	}

	private NotificationEvent createNotificationEvent(
		Chat chat,
		NotificationType notificationType,
		List<Recipient> recipients
	) {
		Moim moim = chat.getMoim();

		return NotificationEvent.chatEvent(
			notificationType,
			moim.getTitle(),
			ChatNotificationMessage.create(chat, notificationType),
			getChatRoomUrl(moim.getDarakbangId(), moim.getId()),
			recipients,
			moim.getDarakbangId(),
			moim.getId()
		);
	}

	static class ChatNotificationMessage {

		public static String create(Chat chat, NotificationType type) {
			if (type.isConfirmedType()) {
				return confirmedChatMessage(chat, type);
			}
			if (type == NotificationType.NEW_CHAT) {
				DarakbangMember sender = chat.getDarakbangMember();
				return sender.getNickname() + ": " + chat.getContent();
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}

		private static String confirmedChatMessage(Chat chat, NotificationType type) {
			String moimName = chat.getMoim().getTitle();
			if (type == NotificationType.MOIM_PLACE_CONFIRMED) {
				return moimName + " 모임 장소가 확정되었어요!";
			}
			return moimName + " 모임 시간이 확정되었어요!";
		}
	}
}
