package mouda.backend.notification.implement.subscription;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.notification.domain.Subscription;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.entity.UnsubscribedChatRooms;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@SpringBootTest
class SubscriptionFinderTest extends DarakbangSetUp {

	@Autowired
	private SubscriptionWriter subscriptionWriter;

	@Autowired
	private SubscriptionFinder subscriptionFinder;

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@DisplayName("구독이 존재하지 않으면 새로 생성한 뒤 반환한다.")
	@Test
	void readSubscription_WhenSubscriptionNotExist() {
		Subscription subscription = subscriptionFinder.readSubscription(darakbangHogee);

		assertThat(subscription.getUnsubscribedChatRooms()).isEmpty();
		assertThat(subscription.isSubscribedMoimCreate()).isTrue();
	}

	@DisplayName("기존 구독 정보가 조회하면 그대로 반환한다.")
	@Test
	void readSubscription_WhenSubscriptionExist() {
		// given
		subscriptionRepository.save(SubscriptionEntity.builder()
			.memberId(darakbangHogee.getMemberId())
			.unsubscribedChats(List.of(UnsubscribedChatRooms.create(1L, 10L)))
			.build());
		subscriptionWriter.changeMoimSubscription(darakbangHogee);

		// when
		Subscription result = subscriptionFinder.readSubscription(darakbangHogee);

		// then
		assertThat(result.isSubscribedMoimCreate()).isFalse();
		assertThat(result.getUnsubscribedChatRooms()).hasSize(1);
		assertThat(result.isSubscribedChatRoom(1L, 10L)).isFalse();
	}
}
