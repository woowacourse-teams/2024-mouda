package mouda.backend.notification.domain.notification;

import org.springframework.stereotype.Component;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOIM_CREATED)
public class MoimCreatedNotificationBuilder extends MoimNotificationBuilder {

	public MoimCreatedNotificationBuilder(MoudaNotificationRepository moudaNotificationRepository) {
		super(moudaNotificationRepository);
	}

	@Override
	public MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember sender) {
		NotificationType notificationType = NotificationType.MOIM_CREATED;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(moim.getTitle()))
			.targetUrl(baseUrl + String.format(moimUrl, darakbangId, moim.getId()))
			.build();
		return moudaNotificationRepository.save(notification);
	}
}
