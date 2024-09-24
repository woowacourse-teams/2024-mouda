package mouda.backend.notification.presentation.response;

import java.util.List;

import mouda.backend.notification.domain.MemberNotification;

public record NotificationFindAllResponses(
	List<NotificationFindAllResponse> notifications
) {

	public static NotificationFindAllResponses toResponse(List<MemberNotification> memberNotifications) {
		return new NotificationFindAllResponses(
			memberNotifications
				.stream()
				.map(MemberNotification::getMoudaNotification)
				.map(NotificationFindAllResponse::from)
				.toList()
		);
	}
}
