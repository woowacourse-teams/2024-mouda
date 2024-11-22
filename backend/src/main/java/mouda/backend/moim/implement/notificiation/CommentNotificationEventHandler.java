package mouda.backend.moim.implement.notificiation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.notificiation.event.CommentNotificationEvent;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;
import mouda.backend.notification.implement.NotificationProcessor;

@Component
public class CommentNotificationEventHandler extends AbstractMoimRelatedNotificationEventHandler {

	private final CommentRecipientFinder commentRecipientFinder;

	public CommentNotificationEventHandler(UrlConfig urlConfig, NotificationProcessor notificationProcessor,
		CommentRecipientFinder commentRecipientFinder) {
		super(urlConfig, notificationProcessor);
		this.commentRecipientFinder = commentRecipientFinder;
	}

	@Async
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = CommentNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handleCommentNotificationEvent(CommentNotificationEvent event) {
		Comment comment = event.getComment();
		DarakbangMember author = event.getAuthor();

		CommentRecipients commentRecipients = commentRecipientFinder.getAllRecipient(comment);

		commentRecipients.getRecipients()
			.forEach((type, recipients) -> processNotification(type, recipients, comment, author));
	}

	private void processNotification(
		NotificationType notificationType, List<Recipient> recipients, Comment comment, DarakbangMember author
	) {
		Moim moim = comment.getMoim();
		NotificationPayload payload = NotificationPayload.createNonChatPayload(
			notificationType,
			moim.getTitle(),
			CommentNotificationMessage.create(author.getNickname(), notificationType),
			getMoimUrl(moim.getDarakbangId(), moim.getId()),
			recipients
		);

		notificationProcessor.process(payload);
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
