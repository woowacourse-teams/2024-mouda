package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
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

// todo: 멤버 필터링 & 알림 저장 & 전송 이벤트 발행 코드 작성
@Component
public class MoimNotificationSender extends AbstractNotificationSender {

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
		NotificationEvent notificationEvent = new NotificationEvent(notificationType, darakbang.getName(),
			notificationType.createMessage(moim.getTitle()), getMoimUrl(darakbang.getId(), moim.getId()), recipients);
		eventPublisher.publishEvent(notificationEvent);
	}

	public void sendMoimStatusChangedNotification(Moim moim, NotificationType notificationType) {
		List<Recipient> recipients = moimRecipientFinder.getMoimStatusChangedNotificationRecipients(moim.getId());
		Darakbang darakbang = darakbangRepository.findById(moim.getDarakbangId())
			.orElseThrow(IllegalArgumentException::new);
		NotificationEvent notificationEvent = new NotificationEvent(notificationType, darakbang.getName(),
			notificationType.createMessage(moim.getTitle()), getMoimUrl(darakbang.getId(), moim.getId()), recipients);

		eventPublisher.publishEvent(notificationEvent);
	}
}
