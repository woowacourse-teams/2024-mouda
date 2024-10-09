package mouda.backend.notification.presentation.response;

import java.time.LocalDateTime;

import mouda.backend.notification.domain.MemberNotification;

public record MemberNotificationFindResponse(
	String message,
	LocalDateTime createdAt,
	String type,
	String redirectUrl
) {

	public static MemberNotificationFindResponse from(MemberNotification notification) {
		return new MemberNotificationFindResponse(
			notification.getMessage(),
			notification.getCreatedAt(),
			notification.getType(),
			notification.getRedirectUrl()
		);
	}
}
