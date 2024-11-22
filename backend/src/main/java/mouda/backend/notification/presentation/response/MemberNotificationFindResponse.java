package mouda.backend.notification.presentation.response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import mouda.backend.notification.domain.MemberNotification;

public record MemberNotificationFindResponse(
	String message,
	String createdAt,
	String type,
	String redirectUrl
) {

	public static MemberNotificationFindResponse from(MemberNotification notification) {
		return new MemberNotificationFindResponse(
			notification.getMessage(),
			parseTime(notification.getCreatedAt()),
			notification.getType(),
			notification.getRedirectUrl()
		);
	}


	private static String parseTime(LocalDateTime notificationCreatedAt) {
		LocalDateTime now = LocalDateTime.now();
		long minutes = notificationCreatedAt.until(now, ChronoUnit.MINUTES);
		long hours = notificationCreatedAt.until(now, ChronoUnit.HOURS);
		long days = notificationCreatedAt.until(now, ChronoUnit.DAYS);

		if (minutes == 0) {
			return "방금 전";
		}
		if (minutes < 60) {
			return minutes + "분 전";
		}
		if (hours < 24) {
			return hours + "시간 전";
		}
		return days + "일 전";
	}
}
