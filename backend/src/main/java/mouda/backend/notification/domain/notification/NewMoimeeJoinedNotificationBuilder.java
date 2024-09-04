package mouda.backend.notification.domain.notification;

import org.springframework.stereotype.Component;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.NEW_MOIMEE_JOINED)
public class NewMoimeeJoinedNotificationBuilder extends MoimNotificationBuilder {

	public NewMoimeeJoinedNotificationBuilder(
		MoudaNotificationRepository moudaNotificationRepository) {
		super(moudaNotificationRepository);
	}

	@Override
	public MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember sender) {
		NotificationType notificationType = NotificationType.NEW_MOIMEE_JOINED;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(sender.getNickname()))
			.targetUrl(baseUrl + String.format(moimUrl, darakbangId, moim.getId()))
			.build();

		return moudaNotificationRepository.save(notification);
	}
}
