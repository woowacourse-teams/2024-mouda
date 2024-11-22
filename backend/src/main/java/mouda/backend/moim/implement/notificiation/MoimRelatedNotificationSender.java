package mouda.backend.moim.implement.notificiation;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.notificiation.event.ChamyoNotificationEvent;
import mouda.backend.moim.implement.notificiation.event.CommentNotificationEvent;
import mouda.backend.moim.implement.notificiation.event.MoimCreateNotificationEvent;
import mouda.backend.moim.implement.notificiation.event.MoimEditedNotificationEvent;
import mouda.backend.moim.implement.notificiation.event.MoimStatusChangeNotificationEvent;
import mouda.backend.notification.domain.NotificationType;

@Component
@RequiredArgsConstructor
public class MoimRelatedNotificationSender {

	private final ApplicationEventPublisher eventPublisher;

	public void sendMoimCreatedNotification(Moim moim, DarakbangMember host, NotificationType notificationType) {
		MoimCreateNotificationEvent event = MoimCreateNotificationEvent.builder()
			.moim(moim)
			.host(host)
			.notificationType(notificationType)
			.build();

		eventPublisher.publishEvent(event);
	}

	public void sendMoimEditedNotification(Moim moim, String oldMoimTitle, NotificationType notificationType) {
		MoimEditedNotificationEvent event = MoimEditedNotificationEvent.builder()
			.moim(moim)
			.oldMoimTitle(oldMoimTitle)
			.notificationType(notificationType)
			.build();

		eventPublisher.publishEvent(event);
	}

	public void sendMoimStatusChangeNotification(Moim moim, NotificationType notificationType) {
		MoimStatusChangeNotificationEvent event = MoimStatusChangeNotificationEvent.builder()
			.moim(moim)
			.notificationType(notificationType)
			.build();

		eventPublisher.publishEvent(event);
	}

	public void sendChamyoNotification(Chamyo chamyo, NotificationType notificationType) {
		ChamyoNotificationEvent event = ChamyoNotificationEvent.builder()
			.chamyo(chamyo)
			.notificationType(notificationType)
			.build();

		eventPublisher.publishEvent(event);
	}

	public void sendCommentNotification(Comment comment, DarakbangMember darakbangMember) {
		CommentNotificationEvent event = CommentNotificationEvent.builder()
			.comment(comment)
			.author(darakbangMember)
			.build();

		eventPublisher.publishEvent(event);
	}
}
