package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.implement.finder.ChamyoRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Component
public class ChamyoNotificationSender extends AbstractMoimNotificationSender {

	private final ChamyoRecipientFinder chamyoRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public ChamyoNotificationSender(UrlConfig urlConfig, ChamyoRecipientFinder chamyoRecipientFinder,
		ApplicationEventPublisher eventPublisher) {
		super(urlConfig);
		this.chamyoRecipientFinder = chamyoRecipientFinder;
		this.eventPublisher = eventPublisher;
	}

	public void sendChamyoNotification(long moimId, DarakbangMember updatedMember, NotificationType notificationType) {
		List<Recipient> recipients = chamyoRecipientFinder.getChamyoNotificationRecipients(moimId, updatedMember);
		NotificationEvent notificationEvent = NotificationEvent.nonChatEvent(
			notificationType,
			updatedMember.getDarakbang().getName(),
			ChamyoNotificationMessage.create(updatedMember.getNickname(), notificationType),
			getMoimUrl(updatedMember.getDarakbang().getId(), moimId),
			recipients
		);

		eventPublisher.publishEvent(notificationEvent);
	}

	static class ChamyoNotificationMessage {

		public static String create(String updatedMemberName, NotificationType type) {
			if (type == NotificationType.NEW_MOIMEE_JOINED) {
				return updatedMemberName + "님이 모임에 참여했어요!";
			}
			if (type == NotificationType.MOIMEE_LEFT) {
				return updatedMemberName + "님이 참여를 취소했어요!";
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}
	}
}
