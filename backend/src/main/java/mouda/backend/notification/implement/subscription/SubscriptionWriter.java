package mouda.backend.notification.implement.subscription;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class SubscriptionWriter {

	private final SubscriptionRepository subscriptionRepository;
}
