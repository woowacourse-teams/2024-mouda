package mouda.backend.notification.presentation.response;

import java.util.List;

public record NotificationFindAllResponses(
	List<NotificationFindAllResponse> notifications
) {
}
