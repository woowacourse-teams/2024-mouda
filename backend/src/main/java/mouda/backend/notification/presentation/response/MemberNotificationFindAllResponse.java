package mouda.backend.notification.presentation.response;

import java.util.List;

import mouda.backend.notification.domain.MemberNotification;

public record MemberNotificationFindAllResponse(
	List<MemberNotificationFindResponse> notifications
) {

	public static MemberNotificationFindAllResponse from(List<MemberNotification> notifications) {
		List<MemberNotificationFindResponse> responses = notifications.stream()
			.map(MemberNotificationFindResponse::from)
			.toList();

		return new MemberNotificationFindAllResponse(responses);
	}
}
