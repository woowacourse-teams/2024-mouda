package mouda.backend.notification.implement.filter;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Component
@RequiredArgsConstructor
public class SubscriptionFilterRegistry {

	private final List<SubscriptionFilter> subscriptionFilters;

	public SubscriptionFilter getFilter(NotificationType notificationType) {
		List<SubscriptionFilter> filters = subscriptionFilters.stream()
			.filter(subscriptionFilter -> subscriptionFilter.support(notificationType))
			.toList();

		if (filters.isEmpty()) {
			throw new NotificationException(HttpStatus.NOT_FOUND, NotificationErrorMessage.FILTER_NOT_FOUND);
		}
		if (filters.size() > 1) {
			throw new NotificationException(HttpStatus.INTERNAL_SERVER_ERROR, NotificationErrorMessage.FILTER_NOT_UNIQUE);
		}
		return filters.get(0);
	}
}
