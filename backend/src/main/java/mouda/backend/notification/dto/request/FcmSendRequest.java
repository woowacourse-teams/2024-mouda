package mouda.backend.notification.dto.request;

public record FcmSendRequest(
	String token,
	String title,
	String body
) {
}
