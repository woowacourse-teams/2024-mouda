package mouda.backend.notification.dto.response;

public record NotificationFindAllResponse(
	String message,
	String createdAt,
	String type
) {
}
