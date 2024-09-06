package mouda.backend.notification.domain.notification;

import org.springframework.stereotype.Component;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.NEW_COMMENT)
public class NewCommentNotificationBuilder extends MoimNotificationBuilder {

	public NewCommentNotificationBuilder(
		MoudaNotificationRepository moudaNotificationRepository) {
		super(moudaNotificationRepository);
	}

	@Override
	public MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember author) {
		MoudaNotification notification = MoudaNotification.builder()
			.type(NotificationType.NEW_COMMENT)
			.body(NotificationType.NEW_COMMENT.createMessage(author.getNickname()))
			.targetUrl(baseUrl + String.format(moimUrl, darakbangId, moim.getId()))
			.build();

		return moudaNotificationRepository.save(notification);
	}
}
