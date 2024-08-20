package mouda.backend.notification.dto.request;

public record FcmTokenRefreshRequest(
        String oldToken,
        String newToken
) {
}
