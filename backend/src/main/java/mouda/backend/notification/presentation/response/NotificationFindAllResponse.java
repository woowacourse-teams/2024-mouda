package mouda.backend.notification.presentation.response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import lombok.Builder;
import mouda.backend.notification.domain.MoudaNotification;

@Builder
public record NotificationFindAllResponse(
	String message,
	String createdAt,
	String type,
	String redirectUrl
) {

	public static NotificationFindAllResponse from(MoudaNotification moudaNotification) {
		return NotificationFindAllResponse.builder()
			.type(moudaNotification.getType().name())
			.message(moudaNotification.getBody())
			.createdAt(parseTime(moudaNotification.getCreatedAt()))
			.redirectUrl(moudaNotification.getTargetUrl())
			.build();
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
