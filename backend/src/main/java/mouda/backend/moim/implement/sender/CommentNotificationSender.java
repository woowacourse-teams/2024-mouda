package mouda.backend.moim.implement.sender;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.CommentRecipient;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.CommentRecipientFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Component
public class CommentNotificationSender extends AbstractMoimNotificationSender {

	private final CommentRecipientFinder commentRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;

	public CommentNotificationSender(UrlConfig urlConfig, CommentRecipientFinder commentRecipientFinder,
		ApplicationEventPublisher eventPublisher) {
		super(urlConfig);
		this.commentRecipientFinder = commentRecipientFinder;
		this.eventPublisher = eventPublisher;
	}

	public void sendCommentNotification(Comment comment, DarakbangMember author) {
		List<CommentRecipient> commentRecipients = commentRecipientFinder.getAllRecipient(comment);

		commentRecipients.forEach(commentRecipient -> {
			sendNotification(commentRecipient, comment, author);
		});
	}

	private void sendNotification(CommentRecipient commentRecipient, Comment comment, DarakbangMember author) {
		NotificationType notificationType = commentRecipient.getNotificationType();
		List<Recipient> recipients = commentRecipient.getRecipients();
		Moim moim = comment.getMoim();
		NotificationEvent notificationEvent = NotificationEvent.nonChatEvent(
			notificationType,
			moim.getTitle(),
			CommentNotificationMessage.create(author.getNickname(), notificationType),
			getMoimUrl(moim.getDarakbangId(), moim.getId()),
			recipients
		);

		eventPublisher.publishEvent(notificationEvent);
	}

	static class CommentNotificationMessage {

		public static String create(String author, NotificationType type) {
			if (type == NotificationType.NEW_COMMENT) {
				return author + "님이 댓글을 남겼어요!";
			}
			if (type == NotificationType.NEW_REPLY) {
				return author + "님이 답글을 남겼어요!";
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}
	}
}
