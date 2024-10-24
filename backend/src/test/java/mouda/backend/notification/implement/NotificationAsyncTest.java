package mouda.backend.notification.implement;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;

@SpringBootTest
public class NotificationAsyncTest extends DarakbangSetUp {

	@Autowired
	private NotificationProcessor notificationProcessor;

	@Autowired
	private MemberNotificationRepository notificationRepository;

	@MockBean
	private NotificationSender notificationSender;

	@DisplayName("알림 전송 과정에서 예외가 발생해도 회원의 알림은 저장된다.")
	@Test
	void asyncWhenNotificationSend() {
		// given
		String title = "비동기 확인 ~";
		String body = "비동기동비";
		NotificationPayload payload = NotificationPayload.createNonChatPayload(
			NotificationType.MOIM_CREATED,
			title,
			body,
			"url",
			List.of(Recipient.builder()
				.memberId(darakbangAnna.getId())
				.darakbangMemberId(darakbangAnna.getId())
				.build())
		);

		// when
		doThrow(new RuntimeException("삐용12"))
			.when(notificationSender).sendNotification(any(CommonNotification.class), anyList());

		notificationProcessor.process(payload);

		// then
		Optional<MemberNotificationEntity> notificationOptional = notificationRepository.findById(
			getNotificationId(title, body));
		assertThat(notificationOptional).isNotEmpty();

		MemberNotificationEntity notification = notificationOptional.get();
		assertThat(notification.getTitle()).isEqualTo(title);
		assertThat(notification.getBody()).isEqualTo(body);
	}

	private long getNotificationId(String title, String body) {
		return notificationRepository.findAll().stream()
			.filter(notification -> notification.getTitle().equals(title) && notification.getBody().equals(body))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new)
			.getId();
	}
}
