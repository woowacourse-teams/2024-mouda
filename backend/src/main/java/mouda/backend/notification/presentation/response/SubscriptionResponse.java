package mouda.backend.notification.presentation.response;

import lombok.Builder;

@Builder
public record SubscriptionResponse(
	boolean isSubscribed
) {
}
