package mouda.backend.notification.dto.response;

import lombok.Builder;

@Builder
public record NotificationFindAllResponse(
	String message,
	String createdAt,
	String type
) {
}
