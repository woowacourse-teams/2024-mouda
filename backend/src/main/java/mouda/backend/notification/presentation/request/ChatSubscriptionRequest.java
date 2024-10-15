package mouda.backend.notification.presentation.request;

import jakarta.validation.constraints.NotNull;

public record ChatSubscriptionRequest(
	@NotNull
	Long darakbangId,

	@NotNull
	Long chatRoomId
) {
}
