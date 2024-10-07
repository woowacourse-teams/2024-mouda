package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.implement.finder.CommentRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class CommentNotificationSender {

	private final CommentRecipientFinder commentRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public void sendCommentNotification(Comment comment, DarakbangMember author, NotificationType notificationType) {
		List<Recipient> recipients;
		if (comment.isChild()) {
			recipients = commentRecipientFinder.getNewCommentNotificationRecipients(comment.getMoim().getId(), author);
		} else {
			recipients = commentRecipientFinder.getNewReplyNotificationRecipients(comment);
		}
		NotificationEvent notificationEvent = new NotificationEvent(notificationType, comment.getMoim().getTitle(), notificationType.createMessage(author.getNickname()), recipients);

		eventPublisher.publishEvent(notificationEvent);
	}
}
