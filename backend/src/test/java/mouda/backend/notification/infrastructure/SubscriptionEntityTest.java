package mouda.backend.notification.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.entity.UnsubscribedChatRooms;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@SpringBootTest
public class SubscriptionEntityTest {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Test
	public void testSaveAndRetrieveSubscriptionEntity() {
		// given: 구독 정보 JSON 생성
		UnsubscribedChatRooms subscription1 = new UnsubscribedChatRooms(1L, Arrays.asList(1L, 2L, 3L));
		UnsubscribedChatRooms subscription2 = new UnsubscribedChatRooms(2L, Arrays.asList(4L, 5L, 6L));
		List<UnsubscribedChatRooms> subscriptions = Arrays.asList(subscription1, subscription2);

		SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
			.memberId(100L)
			.unsubscribedChats(subscriptions)
			.build();

		// when: 엔티티 저장
		SubscriptionEntity savedEntity = subscriptionRepository.save(subscriptionEntity);

		// then: 저장된 데이터 다시 조회
		Optional<SubscriptionEntity> retrievedEntityOpt = subscriptionRepository.findById(savedEntity.getId());
		assertThat(retrievedEntityOpt).isPresent();

		SubscriptionEntity retrievedEntity = retrievedEntityOpt.get();
		assertThat(retrievedEntity.getMemberId()).isEqualTo(100L);
		assertThat(retrievedEntity.isMoimCreate()).isTrue();

		System.out.println(retrievedEntityOpt);

		// JSON 데이터 비교
		List<UnsubscribedChatRooms> retrievedSubscriptions = retrievedEntity.getUnsubscribedChats();
		assertThat(retrievedSubscriptions).hasSize(2);
		assertThat(retrievedSubscriptions.get(0).getDarakbangId()).isEqualTo(1L);
		assertThat(retrievedSubscriptions.get(0).getChatRoomIds()).containsExactly(1L, 2L, 3L);
	}
}
