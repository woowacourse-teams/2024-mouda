package mouda.backend.notification.implement.fcm;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;

import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.NotificationType;

@SpringBootTest
class FcmMessageFactoryTest {

	@Autowired
	private FcmMessageFactory fcmMessageFactory;

	@DisplayName("토큰 500개당 1개의 메시지가 생성된다.")
	@Test
	void createMessage() {
		// given
		CommonNotification notification = CommonNotification.builder()
			.title("title")
			.body("body")
			.redirectUrl("redirectUrl")
			.type(NotificationType.MOIM_CREATED)
			.build();
		int tokenCount = 5196;
		List<String> tokens = createTokens(tokenCount);

		// when
		List<MulticastMessage> messages = fcmMessageFactory.createMessage(notification, tokens);
		int expectedSize = calculateExpectedSize(tokenCount);

		// then
		assertThat(messages).hasSize(expectedSize);
	}

	@DisplayName("토큰이 500개 미만일 때는 1개의 메시지가 생성된다.")
	@Test
	void createMessage_WhenTokenCountIsLessThan500() {
		// given
		CommonNotification notification = CommonNotification.builder()
			.title("title")
			.body("body")
			.redirectUrl("redirectUrl")
			.type(NotificationType.MOIM_CREATED)
			.build();
		int tokenCount = 499;
		List<String> tokens = createTokens(tokenCount);

		// when
		List<MulticastMessage> messages = fcmMessageFactory.createMessage(notification, tokens);

		// then
		assertThat(messages).hasSize(1);
	}

	@DisplayName("각 플랫폼별 설정이 반영된 메시지가 생성된다.")
	@Test
	void createMessage_WithConfig() {
		CommonNotification notification = CommonNotification.builder()
			.title("title")
			.body("body")
			.redirectUrl("redirectUrl")
			.type(NotificationType.MOIM_CREATED)
			.build();
		List<String> tokens = createTokens(1001);

		List<MulticastMessage> messages = fcmMessageFactory.createMessage(notification, tokens);
		List<WebpushConfig> allWebpushConfigs = messages.stream()
			.map(message -> getFieldFromMulticastMessage("webpushConfig", WebpushConfig.class, message))
			.toList();

		assertThat(allWebpushConfigs).doesNotContainNull();

		// WebpushConfig 는 static 이므로 모든 객체가 동일한 참조를 가져야 한다.
		assertThat(allWebpushConfigs).allMatch(webpushConfig -> webpushConfig == allWebpushConfigs.get(0));
	}


	private List<String> createTokens(int size) {
		return IntStream.rangeClosed(1, size)
			.mapToObj(i -> "token" + i)
			.toList();
	}

	private int calculateExpectedSize(int tokenCount) {
		int quotient = (int) tokenCount / 500;
		int remainder = tokenCount % 500;

		if (remainder == 0) {
			return quotient;
		}
		return quotient + 1;
	}

	private <T> T getFieldFromMulticastMessage(String name, Class<T> type, MulticastMessage message) {
		try {
			Field field = message.getClass().getDeclaredField(name);
			field.setAccessible(true);

			return type.cast(field.get(message));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
