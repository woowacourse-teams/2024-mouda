package mouda.backend.notification.implement.subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.Subscription;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.entity.UnsubscribedChatRooms;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class SubscriptionFinder {

	private final SubscriptionRepository subscriptionRepository;

	public Subscription readSubscription(DarakbangMember darakbangMember) {
		return readSubscription(darakbangMember.getMemberId());
	}

	public Subscription readSubscription(long memberId) {
		Optional<SubscriptionEntity> subscriptionEntityOptional = subscriptionRepository.findByMemberId(memberId);

		if (subscriptionEntityOptional.isPresent()) {
			return convertToSubscription(subscriptionEntityOptional.get());
		}

		SubscriptionEntity createdSubscriptionEntity = subscriptionRepository.save(SubscriptionEntity.builder()
			.memberId(memberId)
			.unsubscribedChats(new ArrayList<>())
			.build());
		return convertToSubscription(createdSubscriptionEntity);
	}

	private Subscription convertToSubscription(SubscriptionEntity subscriptionEntity) {
		Map<Long, List<Long>> unsubscribedChatRooms = subscriptionEntity.getUnsubscribedChats().stream()
			.collect(Collectors.toConcurrentMap(
				UnsubscribedChatRooms::getDarakbangId,
				UnsubscribedChatRooms::getChatRoomIds
			));

		return Subscription.builder()
			.isSubscribedMoimCreate(subscriptionEntity.isSubscribedMoimCreate())
			.unsubscribedChatRooms(unsubscribedChatRooms)
			.build();
	}
}
