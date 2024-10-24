package mouda.backend.notification.domain;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NotificationSendEvent {

	private final CommonNotification notification;
	private final List<Recipient> recipients;
	private final Long darakbangId;
	private final Long chatRoomId;

	public static NotificationSendEvent from(NotificationPayload payload) {
		validate(payload);
		return NotificationSendEvent.builder()
			.notification(payload.toCommonNotification())
			.recipients(payload.getRecipients())
			.darakbangId(payload.getDarakbangId())
			.chatRoomId(payload.getChatRoomId())
			.build();
	}

	private static void validate(NotificationPayload payload) {
		NotificationType notificationType = payload.getNotificationType();
		Long chatRoomId = payload.getChatRoomId();
		Long darakbangId = payload.getDarakbangId();

		if (notificationType.isChatType() && (chatRoomId == null || darakbangId == null)) {
			throw new NotificationException(HttpStatus.BAD_REQUEST,
				NotificationErrorMessage.NULL_ID_VALUES_FOR_CHAT_NOTIFICATION);
		}
	}
}
