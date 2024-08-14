package mouda.backend.notification.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.notification.dto.request.FcmSendRequest;

@SpringBootTest
class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;

	@DisplayName("외부 API 호출 테스트")
	@Test
	void send_notification() {
		FcmSendRequest fcmSendRequest = new FcmSendRequest(
			"fPRy7x-vVX2PN1hiLLoKGN:APA91bFkBSt3vtXk_q_OlOtWtpRSEH35DP_825VUwM0Yt3DTeER9p1JIdzmh3KbCo9iUA8cPXHFLB6uqM1bxa4nbuUIY6LXTxYeZ0D7_gYvtNqoZkRbbtmoL48qj61nkBk8xg5DXu654",
			"title", "body");

		Assertions.assertThatNoException().isThrownBy(
			() -> notificationService.sendMessage(fcmSendRequest)
		);
	}
}
