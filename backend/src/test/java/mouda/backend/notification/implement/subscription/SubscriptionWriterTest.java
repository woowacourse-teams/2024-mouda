package mouda.backend.notification.implement.subscription;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;
import mouda.backend.notification.infrastructure.entity.UnsubscribedChatRooms;
import mouda.backend.notification.infrastructure.repository.SubscriptionRepository;

@SpringBootTest
class SubscriptionWriterTest extends DarakbangSetUp {

	@Autowired
	private SubscriptionWriter subscriptionWriter;

	@Autowired
	private SubscriptionRepository subscriptionRepository;


	@DisplayName("모임 생성에 대한 알림 허용 여부를 변경한다.")
	@Nested
	class MoimCreateSubscriptionTest {

		@DisplayName("구독 정보가 존재하지 않으면 새로 만든 뒤 비허용 상태로 변경한다.")
		@Test
		void changeMoimCreateSubscription_WhenNotExist() {
			// when
			subscriptionWriter.changeMoimSubscription(darakbangHogee);

			// then
			Optional<SubscriptionEntity> SubscriptionOptional = subscriptionRepository.findByMemberId(
				darakbangHogee.getMemberId());
			assertThat(SubscriptionOptional.isPresent()).isTrue();

			SubscriptionEntity subscription = SubscriptionOptional.get();
			assertThat(subscription.isSubscribedMoimCreate()).isFalse();
		}

		@DisplayName("알림 허용 상태에서 비허용 상태로 변경한다.")
		@Test
		void changeMoimCreateSubscription_WhenSubscribedMoimCreate() {
			// given
			subscriptionRepository.save(SubscriptionEntity.builder()
				.memberId(darakbangHogee.getMemberId())
				.unsubscribedChats(new ArrayList<>())
				.build());

			// when
			subscriptionWriter.changeMoimSubscription(darakbangHogee);

			// then
			Optional<SubscriptionEntity> SubscriptionOptional = subscriptionRepository.findByMemberId(
				darakbangHogee.getMemberId());
			assertThat(SubscriptionOptional.isPresent()).isTrue();

			SubscriptionEntity subscription = SubscriptionOptional.get();
			assertThat(subscription.isSubscribedMoimCreate()).isFalse();
		}

		@DisplayName("알림 비허용 상태에서 허용 상태로 변경한다.")
		@Test
		void changeMoimCreateSubscription_WhenUnsubscribedMoimCreate() {
			// given
			subscriptionRepository.save(SubscriptionEntity.builder()
				.memberId(darakbangHogee.getMemberId())
				.unsubscribedChats(new ArrayList<>())
				.build());
			subscriptionWriter.changeMoimSubscription(darakbangHogee);

			// when
			subscriptionWriter.changeMoimSubscription(darakbangHogee);

			// then
			Optional<SubscriptionEntity> SubscriptionOptional = subscriptionRepository.findByMemberId(
				darakbangHogee.getMemberId());
			assertThat(SubscriptionOptional.isPresent()).isTrue();

			SubscriptionEntity subscription = SubscriptionOptional.get();
			assertThat(subscription.isSubscribedMoimCreate()).isTrue();
		}
	}

	@DisplayName("특정 채팅방에 대한 알림 허용 여부를 변경한다.")
	@Nested
	class ChatRoomSubscriptionTest {

		@DisplayName("구독 정보가 존재하지 않으면 새로 만든 뒤 비허용 상태로 변경한다.")
		@Test
		void changeChatRoomSubscription_WhenNotExist() {
			// when
			subscriptionWriter.changeChatRoomSubscription(darakbangHogee, 1L, 10L);

			// then
			Optional<SubscriptionEntity> SubscriptionOptional = subscriptionRepository.findByMemberId(
				darakbangHogee.getMemberId());
			assertThat(SubscriptionOptional.isPresent()).isTrue();

			SubscriptionEntity subscription = SubscriptionOptional.get();
			UnsubscribedChatRooms unsubscribedChatRooms = subscription.getUnsubscribedChats().get(0);
			assertThat(unsubscribedChatRooms.getDarakbangId()).isEqualTo(1L);
			assertThat(unsubscribedChatRooms.getChatRoomIds().contains(10L)).isTrue();
		}

		@DisplayName("알림 허용 상태에서 비허용 상태로 변경한다.")
		@Test
		void changeChatRoomSubscription_WhenSubscribedChatRoom() {
			// given
			subscriptionRepository.save(SubscriptionEntity.builder()
				.memberId(darakbangHogee.getMemberId())
				.unsubscribedChats(new ArrayList<>())
				.build());

			// when
			subscriptionWriter.changeChatRoomSubscription(darakbangHogee, 1L, 10L);

			// then
			Optional<SubscriptionEntity> SubscriptionOptional = subscriptionRepository.findByMemberId(
				darakbangHogee.getMemberId());
			assertThat(SubscriptionOptional.isPresent()).isTrue();

			SubscriptionEntity subscription = SubscriptionOptional.get();
			UnsubscribedChatRooms unsubscribedChatRooms = subscription.getUnsubscribedChats().get(0);
			assertThat(unsubscribedChatRooms.getDarakbangId()).isEqualTo(1L);
			assertThat(unsubscribedChatRooms.getChatRoomIds().contains(10L)).isTrue();
		}

		@DisplayName("알림 비허용 상태에서 허용 상태로 변경한다.")
		@Test
		void changeChatRoomSubscription_WhenUnSubscribedChatRoom() {
			// given
			subscriptionRepository.save(SubscriptionEntity.builder()
				.memberId(darakbangHogee.getMemberId())
				.unsubscribedChats(new ArrayList<>())
				.build());
			subscriptionWriter.changeChatRoomSubscription(darakbangHogee, 1L, 10L);

			// when
			subscriptionWriter.changeChatRoomSubscription(darakbangHogee, 1L, 10L);

			// then
			Optional<SubscriptionEntity> SubscriptionOptional = subscriptionRepository.findByMemberId(
				darakbangHogee.getMemberId());
			assertThat(SubscriptionOptional.isPresent()).isTrue();

			SubscriptionEntity subscription = SubscriptionOptional.get();
			UnsubscribedChatRooms unsubscribedChatRooms = subscription.getUnsubscribedChats().get(0);
			assertThat(unsubscribedChatRooms.getDarakbangId()).isEqualTo(1L);
			assertThat(unsubscribedChatRooms.getChatRoomIds().contains(10L)).isFalse();
		}
	}
}
