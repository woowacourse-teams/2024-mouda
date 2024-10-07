package mouda.backend.notification.domain;

import java.util.List;

import lombok.Getter;

@Getter
public class NotificationEvent {

	private final NotificationType notificationType;
	private final String title;
	private final String body;
	private final List<Recipient> recipients;
	private final Long darakbangId;
	private final Long chatRoomId;

	public NotificationEvent(NotificationType notificationType, String title, String body, List<Recipient> recipients) {
		this.notificationType = notificationType;
		this.title = title;
		this.body = body;
		this.recipients = recipients;
		this.darakbangId = null;
		this.chatRoomId = null;
	}

	public NotificationEvent(NotificationType notificationType, String title, String body, List<Recipient> recipients, Long darakbangId, Long chatRoomId) {
		this.notificationType = notificationType;
		this.title = title;
		this.body = body;
		this.recipients = recipients;
		this.darakbangId = darakbangId;
		this.chatRoomId = chatRoomId;
	}

	public CommonNotification toCommonNotification() {
		return new CommonNotification(
			notificationType, title, body, "url"
		);
	}
}
