package mouda.backend.notification.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.dto.response.NotificationFindAllResponse;
import mouda.backend.notification.repository.MemberNotificationRepository;
import mouda.backend.notification.repository.MoudaNotificationRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationServiceTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoudaNotificationRepository moudaNotificationRepository;

	@Autowired
	private MemberNotificationRepository memberNotificationRepository;

	@Autowired
	private NotificationService notificationService;

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@DisplayName("회원의 모든 알림을 조회한다.")
	@Test
	void findAllMyNotifications() {
		NotificationType type1 = NotificationType.MOIM_CREATED;
		MoudaNotification notification1 = moudaNotificationRepository.save(MoudaNotification.builder()
			.type(type1)
			.body(type1.createMessage("테스트모임"))
			.targetUrl("test")
			.build());

		NotificationType type2 = NotificationType.NEW_CHAT;
		MoudaNotification notification2 = moudaNotificationRepository.save(MoudaNotification.builder()
			.type(type2)
			.body(type2.createMessage("상돌"))
			.targetUrl("test")
			.build());

		Member member = memberRepository.save(Member.builder()
			.nickname("상돌")
			.kakaoId(1234L)
			.build());

		memberNotificationRepository.save(MemberNotification.builder()
			.memberId(member.getId())
			.moudaNotification(notification1)
			.build());

		memberNotificationRepository.save(MemberNotification.builder()
			.memberId(member.getId())
			.moudaNotification(notification2)
			.build());

		List<NotificationFindAllResponse> responses = notificationService.findAllMyNotifications(member)
			.notifications();

		assertThat(responses).satisfies(res -> {
			assertThat(res).hasSize(2);
			assertThat(res).extracting(NotificationFindAllResponse::message)
				.containsExactly(type1.createMessage("테스트모임"), type2.createMessage("상돌"));
			assertThat(res).extracting(NotificationFindAllResponse::createdAt)
				.containsExactly("방금 전", "방금 전");
			assertThat(res).extracting(NotificationFindAllResponse::type)
				.containsExactly(type1.toString(), type2.toString());
		});
	}
}
