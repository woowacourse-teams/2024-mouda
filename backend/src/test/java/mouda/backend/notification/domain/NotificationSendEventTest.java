package mouda.backend.notification.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import mouda.backend.notification.exception.NotificationException;

class NotificationSendEventTest {

	@DisplayName("알림 타입이 채팅이라면 다락방과 채팅방 ID가 null이 아니어야 한다.")
	@Test
	void fromPayload() {
		NotificationPayload payload = NotificationPayload.createChatPayload(
			NotificationType.MOIM_PLACE_CONFIRMED,
			"모임 제목",
			"메시지",
			"url",
			List.of(),
			1L,
			1L
		);

		assertThatCode(() -> NotificationSendEvent.from(payload)).doesNotThrowAnyException();

		NotificationSendEvent event = NotificationSendEvent.from(payload);
		assertThat(event).isNotNull();
	}

	@DisplayName("알림 타입이 채팅일 때 다락방 ID가 null이면 예외를 던진다.")
	@Test
	void nullDarakbangId() {
		NotificationPayload payload = NotificationPayload.createChatPayload(
			NotificationType.MOIM_PLACE_CONFIRMED,
			"모임 제목",
			"메시지",
			"url",
			List.of(),
			null,
			1L
		);

		assertThatThrownBy(() -> NotificationSendEvent.from(payload))
			.isInstanceOf(NotificationException.class);
	}

	@DisplayName("알림 타입이 채팅일 때 채팅방 ID가 null이면 예외를 던진다.")
	@Test
	void nullChatRoomId() {
		NotificationPayload payload = NotificationPayload.createChatPayload(
			NotificationType.MOIM_PLACE_CONFIRMED,
			"모임 제목",
			"메시지",
			"url",
			List.of(),
			1L,
			null
		);

		assertThatThrownBy(() -> NotificationSendEvent.from(payload))
			.isInstanceOf(NotificationException.class);
	}
}
