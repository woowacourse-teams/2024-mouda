package mouda.backend.notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorMessage {

	// Firebase
	FCM_TOKEN_NOT_FOUND_BY_MEMBER("회원의 FCM 토큰이 존재하지 않아요."),
	FCM_TOKEN_NOT_FOUND_BY_TOKEN("요청한 FCM 토큰이 존재하지 않아요.");

	// Notification

	private final String message;
}
