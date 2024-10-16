package mouda.backend.notification.implement.filter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationType;

@Component
@RequiredArgsConstructor
public class SubscriptionFilterRegistry {

	private final List<SubscriptionFilter> subscriptionFilters;

	public SubscriptionFilter getFilter(NotificationType notificationType) {
		return subscriptionFilters.stream()
			.filter(subscriptionFilter -> subscriptionFilter.support(notificationType))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("no such filter"));
	}
}
