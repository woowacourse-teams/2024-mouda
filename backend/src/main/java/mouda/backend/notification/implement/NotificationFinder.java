package mouda.backend.notification.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;

@Component
@RequiredArgsConstructor
public class NotificationFinder {

	private final MemberNotificationRepository memberNotificationRepository;

}
