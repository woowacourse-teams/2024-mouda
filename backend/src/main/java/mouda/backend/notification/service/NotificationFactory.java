package mouda.backend.notification.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.domain.notification.NotificationBuilderStrategy;

@Service
public class NotificationFactory {

	private final Map<NotificationType, NotificationBuilderStrategy> strategies;

	public NotificationFactory(List<NotificationBuilderStrategy> strategyList) {
		this.strategies = strategyList.stream()
			.collect(
				Collectors.toMap(strategy -> {
						return strategy.getClass().getAnnotation(NotificationTypeProvider.class).value();
					},
					strategy -> strategy));
	}

	public NotificationBuilderStrategy getStrategy(NotificationType type) {
		return strategies.get(type);
	}
}
