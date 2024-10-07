package mouda.backend.notification.implement.filter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class ChatRoomSubscriptionFilter implements SubscriptionFilter {

	private final SubscriptionRepository subscriptionRepository;

	@Override
	public boolean support(NotificationType notificationType) {
		return notificationType == NotificationType.NEW_CHAT;
	}

	@Override
	public List<Recipient> filter(NotificationEvent notificationEvent) {
		return notificationEvent.getRecipients().stream().filter(
			recipient -> {
				SubscriptionEntity subscription = subscriptionRepository.findByMemberId(recipient.getMemberId());
				return subscription.isSubscribedChatRoom(notificationEvent.getDarakbangId(), notificationEvent.getChatRoomId());
			}
		).toList();
	}
}
