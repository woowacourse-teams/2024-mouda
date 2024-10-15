package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.infrastructure.DarakbangRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Component
public class MoimNotificationSender extends AbstractMoimNotificationSender {

	private final ApplicationEventPublisher eventPublisher;
	private final MoimRecipientFinder moimRecipientFinder;
	private final DarakbangRepository darakbangRepository;

	public MoimNotificationSender(UrlConfig urlConfig, ApplicationEventPublisher eventPublisher,
		MoimRecipientFinder moimRecipientFinder, DarakbangRepository darakbangRepository) {
		super(urlConfig);
		this.eventPublisher = eventPublisher;
		this.moimRecipientFinder = moimRecipientFinder;
		this.darakbangRepository = darakbangRepository;
	}

	public void sendMoimCreatedNotification(Moim moim, DarakbangMember author, NotificationType notificationType) {
		List<Recipient> recipients = moimRecipientFinder.getMoimCreatedNotificationRecipients(moim.getDarakbangId(),
			author.getId());
		Darakbang darakbang = darakbangRepository.findById(moim.getDarakbangId())
			.orElseThrow(IllegalArgumentException::new);
		NotificationEvent notificationEvent = NotificationEvent.nonChatEvent(
			notificationType,
			darakbang.getName(),
			MoimNotificationMessage.create(moim.getTitle(), notificationType),
			getMoimUrl(darakbang.getId(), moim.getId()),
			recipients
		);
		eventPublisher.publishEvent(notificationEvent);
	}

	public void sendMoimStatusChangedNotification(Moim moim, NotificationType notificationType) {
		List<Recipient> recipients = moimRecipientFinder.getMoimStatusChangedNotificationRecipients(moim.getId());
		Darakbang darakbang = darakbangRepository.findById(moim.getDarakbangId())
			.orElseThrow(IllegalArgumentException::new);
		NotificationEvent notificationEvent = NotificationEvent.nonChatEvent(
			notificationType,
			darakbang.getName(),
			MoimNotificationMessage.create(moim.getTitle(), notificationType),
			getMoimUrl(darakbang.getId(), moim.getId()),
			recipients
		);

		eventPublisher.publishEvent(notificationEvent);
	}

	static class MoimNotificationMessage {

		public static String create(String moimName, NotificationType type) {
			if (type == NotificationType.MOIM_CREATED) {
				return moimName + " 모임이 만들어졌어요!";
			}
			if (type == NotificationType.MOIMING_COMPLETED) {
				return moimName + " 모집이 마감되었어요!";
			}
			if (type == NotificationType.MOINING_REOPENED) {
				return moimName + " 모집이 재개되었어요!";
			}
			if (type == NotificationType.MOIM_CANCELLED) {
				return moimName + " 모임이 취소되었어요!";
			}
			if (type == NotificationType.MOIM_MODIFIED) {
				return moimName + " 모임 정보가 변경되었어요!";
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}
	}
}
