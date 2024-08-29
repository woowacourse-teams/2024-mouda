package mouda.backend.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Schema(name = "FCM 토크 저장 요청", description = "FCM 토큰을 저장할 때 사용")
public record FcmTokenSaveRequest(
	@NotEmpty
	@Schema(description = "FCM 토큰", minLength = 1)
	String token
) {
}
