package mouda.backend.moim.implement.notificiation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.notificiation.event.ChamyoNotificationEvent;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;
import mouda.backend.notification.implement.NotificationProcessor;

@Component
public class ChamyoNotificationEventHandler extends AbstractMoimRelatedNotificationEventHandler {

	private final ChamyoRecipientFinder chamyoRecipientFinder;

	public ChamyoNotificationEventHandler(
		UrlConfig urlConfig, NotificationProcessor notificationProcessor, ChamyoRecipientFinder chamyoRecipientFinder
	) {
		super(urlConfig, notificationProcessor);
		this.chamyoRecipientFinder = chamyoRecipientFinder;
	}

	@Async
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = ChamyoNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handleChamyoNotificationEvent(ChamyoNotificationEvent event) {
		Moim moim = event.getMoim();
		DarakbangMember updatedMember = event.getUpdatedMember();
		Darakbang darakbang = updatedMember.getDarakbang();

		List<Recipient> recipients = chamyoRecipientFinder.getChamyoNotificationRecipients(moim, updatedMember);

		NotificationPayload payload = NotificationPayload.createNonChatPayload(
			event.getNotificationType(),
			darakbang.getName(),
			ChamyoNotificationMessage.create(updatedMember.getNickname(), moim.getTitle(), event.getNotificationType()),
			getMoimUrl(darakbang.getId(), moim.getId()),
			recipients
		);
		notificationProcessor.process(payload);
	}

	static class ChamyoNotificationMessage {

		public static String create(String updatedMemberName, String moimTitle, NotificationType type) {
			if (type == NotificationType.NEW_MOIMEE_JOINED) {
				return updatedMemberName + "님이 " + moimTitle + " 모임에 참여했어요!";
			}
			if (type == NotificationType.MOIMEE_LEFT) {
				return updatedMemberName + "님이 " + moimTitle + " 모임 참여를 취소했어요!";
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}
	}
}
