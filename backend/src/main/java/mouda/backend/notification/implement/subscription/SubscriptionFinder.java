package mouda.backend.notification.implement.subscription;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class SubscriptionFinder {

	private final SubscriptionRepository subscriptionRepository;

	public boolean isSubscribedMoimCreate(long memberId) {
		SubscriptionEntity subscription = subscriptionRepository.findByMemberId(memberId);
		return subscription.isSubscribedMoimCreate();
	}

	public boolean isSubscribedChat(long chatId, long memberId, long darakbangId) {
		SubscriptionEntity subscription = subscriptionRepository.findByMemberId(memberId);

		return subscription.getChats().stream()
			.anyMatch(chat -> chat.getDarakbangId() == darakbangId && chat.getChatRoomIds().contains(chatId));
	}
}
