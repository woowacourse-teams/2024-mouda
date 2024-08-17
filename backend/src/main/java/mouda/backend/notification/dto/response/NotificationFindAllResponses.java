package mouda.backend.notification.dto.response;

import java.util.List;

public record NotificationFindAllResponses(
	List<NotificationFindAllResponse> notifications
) {
}
