package mouda.backend.notification.implement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.implement.recipientresolver.RecipientResolverStrategy;

@Service
public class RecipientFactory {
	private final Map<NotificationType, RecipientResolverStrategy> strategies;

	public RecipientFactory(List<RecipientResolverStrategy> strategyList) {
		this.strategies = strategyList.stream()
			.collect(
				Collectors.toMap(strategy -> {
						return strategy.getClass().getAnnotation(NotificationTypeProvider.class).value();
					},
					strategy -> strategy));
	}

	public RecipientResolverStrategy getStrategy(NotificationType type) {
		return strategies.get(type);
	}
}
