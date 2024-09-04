package mouda.backend.notification.domain.notification;

import org.springframework.stereotype.Component;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.NEW_REPLY)
public class NewReplyNotificationBuilder extends MoimNotificationBuilder {

	public NewReplyNotificationBuilder(MoudaNotificationRepository moudaNotificationRepository) {
		super(moudaNotificationRepository);
	}

	@Override
	public MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember author) {
		MoudaNotification notification = MoudaNotification.builder()
			.type(NotificationType.NEW_REPLY)
			.body(NotificationType.NEW_REPLY.createMessage(author.getNickname()))
			.targetUrl(baseUrl + String.format(moimUrl, darakbangId, moim.getId()))
			.build();

		return moudaNotificationRepository.save(notification);
	}
}
