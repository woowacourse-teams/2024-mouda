package mouda.backend.moim.implement.notificiation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.implement.notificiation.event.MoimCreateNotificationEvent;
import mouda.backend.moim.implement.notificiation.event.MoimEditedNotificationEvent;
import mouda.backend.moim.implement.notificiation.event.MoimStatusChangeNotificationEvent;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;
import mouda.backend.notification.implement.NotificationProcessor;

@Component
public class MoimNotificationEventHandler extends AbstractMoimRelatedNotificationEventHandler {

	private final MoimRecipientFinder moimRecipientFinder;
	private final DarakbangRepository darakbangRepository;

	public MoimNotificationEventHandler(
		UrlConfig urlConfig, NotificationProcessor notificationProcessor,
		MoimRecipientFinder moimRecipientFinder, DarakbangRepository darakbangRepository
	) {
		super(urlConfig, notificationProcessor);
		this.moimRecipientFinder = moimRecipientFinder;
		this.darakbangRepository = darakbangRepository;
	}

	@Async
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = MoimCreateNotificationEvent.class)
	public void handleMoimCreateNotificationEvent(MoimCreateNotificationEvent event) {
		Moim moim = event.getMoim();
		NotificationType notificationType = event.getNotificationType();

		List<Recipient> recipients = moimRecipientFinder.getMoimCreatedNotificationRecipients(moim.getDarakbangId(),
			event.getHost().getId());
		String message = MoimNotificationMessage.create(moim.getTitle(), notificationType);

		processNotification(notificationType, moim, message, recipients);
	}

	@Async
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = MoimStatusChangeNotificationEvent.class)
	public void handleMoimStatusChangeNotificationEvent(MoimStatusChangeNotificationEvent event) {
		Moim moim = event.getMoim();
		NotificationType notificationType = event.getNotificationType();

		String message = MoimNotificationMessage.create(moim.getTitle(), notificationType);
		processMoimModifiedNotification(moim, notificationType, message);
	}

	@Async
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = MoimEditedNotificationEvent.class)
	public void handleMoimEditedNotificationEvent(MoimEditedNotificationEvent event) {
		Moim moim = event.getMoim();
		NotificationType notificationType = event.getNotificationType();

		String message = MoimNotificationMessage.create(event.getOldMoimTitle(), notificationType);
		processMoimModifiedNotification(moim, notificationType, message);
	}

	private void processMoimModifiedNotification(Moim moim, NotificationType notificationType, String message) {
		List<Recipient> recipients = moimRecipientFinder.getMoimModifiedNotificationRecipients(moim.getId());
		processNotification(notificationType, moim, message, recipients);
	}

	private void processNotification(
		NotificationType notificationType, Moim moim, String message, List<Recipient> recipients
	) {
		Darakbang darakbang = darakbangRepository.findById(moim.getDarakbangId())
			.orElseThrow(() -> new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.DARAKBANG_NOT_FOUND));

		NotificationPayload payload = NotificationPayload.createNonChatPayload(
			notificationType,
			darakbang.getName(),
			message,
			getMoimUrl(darakbang.getId(), moim.getId()),
			recipients
		);

		notificationProcessor.process(payload);
	}
	
	static class MoimNotificationMessage {

		public static String create(String moimTitle, NotificationType type) {
			if (type == NotificationType.MOIM_CREATED) {
				return moimTitle + " 모임이 만들어졌어요!";
			}
			if (type == NotificationType.MOIMING_COMPLETED) {
				return moimTitle + " 모집이 마감되었어요!";
			}
			if (type == NotificationType.MOINING_REOPENED) {
				return moimTitle + " 모집이 재개되었어요!";
			}
			if (type == NotificationType.MOIM_CANCELLED) {
				return moimTitle + " 모임이 취소되었어요!";
			}
			if (type == NotificationType.MOIM_MODIFIED) {
				return moimTitle + " 모임 정보가 변경되었어요!";
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}
	}
}
