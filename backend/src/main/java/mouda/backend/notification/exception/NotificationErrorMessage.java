package mouda.backend.notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorMessage {

	// Firebase
	FCM_TOKEN_NOT_FOUND("회원의 FCM 토큰이 존재하지 않아요."),
	;

	// Notification

	private final String message;
}
