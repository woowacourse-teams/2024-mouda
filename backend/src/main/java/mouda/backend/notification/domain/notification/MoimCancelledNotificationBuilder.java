package mouda.backend.notification.domain.notification;

import org.springframework.stereotype.Component;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOIM_CANCELLED)
public class MoimCancelledNotificationBuilder extends MoimNotificationBuilder {

	public MoimCancelledNotificationBuilder(
		MoudaNotificationRepository moudaNotificationRepository) {
		super(moudaNotificationRepository);
	}

	@Override
	public MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember sender) {
		NotificationType notificationType = NotificationType.MOIM_CANCELLED;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(moim.getTitle()))
			.targetUrl(baseUrl + String.format(moimUrl, darakbangId, moim.getId()))
			.build();

		return moudaNotificationRepository.save(notification);
	}
}
