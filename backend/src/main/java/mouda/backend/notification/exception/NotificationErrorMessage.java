package mouda.backend.notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorMessage {

	NOT_ALLOWED_NOTIFICATION_TYPE("지원하지 않는 알림 타입이에요.");

	private final String message;
}
