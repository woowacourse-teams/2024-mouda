package mouda.backend.notification.implement.subscription;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class SubscriptionWriter {

	private final SubscriptionRepository subscriptionRepository;

	public void changeMoimSubscription(Member member) {
		subscriptionRepository.findByMemberId(member.getId())
			.ifPresentOrElse(
				this::changeMoimSubscription,
				() -> changeMoimSubscription(create(member))
			);
	}

	public void changeChatRoomSubscription(Member member, long darakbangId, long chatRoomId) {
		subscriptionRepository.findByMemberId(member.getId())
			.ifPresentOrElse(
				subscription -> changeChatRoomSubscription(subscription, darakbangId, chatRoomId),
				() -> changeChatRoomSubscription(create(member), darakbangId, chatRoomId)
			);
	}

	private void changeMoimSubscription(SubscriptionEntity subscriptionEntity) {
		subscriptionEntity.changeMoimCreateSubscription();
		subscriptionRepository.save(subscriptionEntity);
	}

	private void changeChatRoomSubscription(SubscriptionEntity subscriptionEntity, long darakbangId, long chatRoomId) {
		subscriptionEntity.changeChatRoomSubscription(darakbangId, chatRoomId);
		subscriptionRepository.save(subscriptionEntity);
	}

	private SubscriptionEntity create(Member member) {
		return subscriptionRepository.save(SubscriptionEntity.builder()
			.memberId(member.getId())
			.unsubscribedChats(new ArrayList<>())
			.build());
	}
}
