package mouda.backend.notification.dto.response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;

@Builder
@Schema(name = "알림 조회 응답", description = "회원의 알림 조회할 때 사용")
public record NotificationFindAllResponse(
	@Schema(description = "알림 내용", example = "새로운 모임이 생성되었습니다.")
	String message,
	@Schema(description = "알림 생성 시간", example = "1시간 전")
	String createdAt,
	@Schema(description = "알림 타입")
	NotificationType type,
	@Schema(description = "알림을 클릭했을 때 이동할 URL", example = "https://example.com/moim/1")
	String redirectUrl
) {

	public static NotificationFindAllResponse from(MoudaNotification moudaNotification) {
		return NotificationFindAllResponse.builder()
			.type(moudaNotification.getType())
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
