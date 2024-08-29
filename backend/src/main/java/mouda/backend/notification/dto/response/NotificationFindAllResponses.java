package mouda.backend.notification.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "모든 알림 조회 응답", description = "회원의 모든 알림을 조회할 때 사용")
public record NotificationFindAllResponses(
	@Schema(description = "알림 목록")
	List<NotificationFindAllResponse> notifications
) {
}
