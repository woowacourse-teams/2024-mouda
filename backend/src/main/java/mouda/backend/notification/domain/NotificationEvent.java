package mouda.backend.notification.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationEvent {

	private final NotificationType notificationType;
	private final String title;
	private final String body;
	private final String redirectUrl;
	private final List<Recipient> recipients;
	private final Long darakbangId;
	private final Long chatRoomId;

	public static NotificationEvent nonChatEvent(
		NotificationType notificationType,
		String title,
		String body,
		String redirectUrl,
		List<Recipient> recipients
	) {
		return new NotificationEvent(
			notificationType, title, body, redirectUrl, recipients, null, null
		);
	}

	public static NotificationEvent chatEvent(
		NotificationType notificationType,
		String title,
		String body,
		String redirectUrl,
		List<Recipient> recipients,
		Long darakbangId,
		Long chatRoomId
	) {
		return new NotificationEvent(
			notificationType, title, body, redirectUrl, recipients, darakbangId, chatRoomId
		);
	}

	public CommonNotification toCommonNotification() {
		return new CommonNotification(
			notificationType, title, body, redirectUrl
		);
	}
}
