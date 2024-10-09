package mouda.backend.notification.implement.subscription;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class SubscriptionWriter {

	private final SubscriptionRepository subscriptionRepository;

	public void changeMoimSubscription(DarakbangMember darakbangMember) {
		subscriptionRepository.findByMemberId(darakbangMember.getMemberId())
			.ifPresentOrElse(
				this::changeMoimSubscription,
				() -> changeMoimSubscription(create(darakbangMember))
			);
	}

	public void changeChatRoomSubscription(DarakbangMember darakbangMember, long darakbangId, long chatRoomId) {
		subscriptionRepository.findByMemberId(darakbangMember.getMemberId())
			.ifPresentOrElse(
				subscription -> changeChatRoomSubscription(subscription, darakbangId, chatRoomId),
				() -> changeChatRoomSubscription(create(darakbangMember), darakbangId, chatRoomId)
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

	private SubscriptionEntity create(DarakbangMember darakbangMember) {
		return subscriptionRepository.save(SubscriptionEntity.builder()
			.memberId(darakbangMember.getMemberId())
			.unsubscribedChats(new ArrayList<>())
			.build());
	}
}
