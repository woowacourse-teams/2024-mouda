package mouda.backend.notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorMessage {

	NOT_ALLOWED_NOTIFICATION_TYPE("지원하지 않는 알림 타입이에요."),
	FILTER_NOT_FOUND("입력된 알림 타입에 대한 구독 필터를 찾을 수 없어요."),
	FILTER_NOT_UNIQUE("입력된 알림 타입에 대한 구독 필터가 유일하지 않아요."),
	NULL_ID_VALUES_FOR_CHAT_NOTIFICATION("채팅 알림을 보내기 위해선 다락방과 채팅방 ID가 필요해요."),
	;

	private final String message;
}
