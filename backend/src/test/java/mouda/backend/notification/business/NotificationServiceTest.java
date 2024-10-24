// package mouda.backend.notification.business;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.List;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import mouda.backend.common.fixture.DarakbangFixture;
// import mouda.backend.common.fixture.DarakbangMemberFixture;
// import mouda.backend.common.fixture.MemberFixture;
// import mouda.backend.darakbang.domain.Darakbang;
// import mouda.backend.darakbang.infrastructure.DarakbangRepository;
// import mouda.backend.darakbangmember.domain.DarakbangMember;
// import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
// import mouda.backend.member.domain.Member;
// import mouda.backend.member.infrastructure.MemberRepository;
// import mouda.backend.notification.domain.MemberNotification;
// import mouda.backend.notification.domain.MoudaNotification;
// import mouda.backend.notification.domain.NotificationType;
// import mouda.backend.notification.infrastructure.MemberNotificationRepository;
// import mouda.backend.notification.infrastructure.MoudaNotificationRepository;
// import mouda.backend.notification.presentation.response.NotificationFindAllResponse;
//
// @SpringBootTest
// class NotificationServiceTest {
//
// 	@Autowired
// 	private DarakbangRepository darakbangRepository;
//
// 	@Autowired
// 	private MemberRepository memberRepository;
//
// 	@Autowired
// 	private DarakbangMemberRepository darakbangMemberRepository;
//
// 	@Autowired
// 	private MoudaNotificationRepository moudaNotificationRepository;
//
// 	@Autowired
// 	private MemberNotificationRepository memberNotificationRepository;
//
// 	@Autowired
// 	private NotificationService notificationService;
//
// 	@DisplayName("회원의 모든 알림을 조회한다.")
// 	@Test
// 	void findAllMyNotifications() {
// 		Darakbang darakbang = DarakbangFixture.getDarakbangWithMouda();
// 		darakbangRepository.save(darakbang);
//
// 		NotificationType type1 = NotificationType.MOIM_CREATED;
// 		MoudaNotification notification1 = moudaNotificationRepository.save(MoudaNotification.builder()
// 			.type(type1)
// 			.body(type1.createMessage("테스트모임"))
// 			.targetUrl("test")
// 			.build());
//
// 		NotificationType type2 = NotificationType.NEW_CHAT;
// 		MoudaNotification notification2 = moudaNotificationRepository.save(MoudaNotification.builder()
// 			.type(type2)
// 			.body(type2.createMessage("상돌"))
// 			.targetUrl("test")
// 			.build());
//
// 		Member member = MemberFixture.getAnna();
// 		memberRepository.save(member);
//
// 		DarakbangMember darakbangMember = DarakbangMemberFixture.getDarakbangMemberWithWooteco(darakbang,
// 			member);
// 		darakbangMemberRepository.save(darakbangMember);
//
// 		memberNotificationRepository.save(MemberNotification.builder()
// 			.memberId(darakbangMember.getMemberId())
// 			.moudaNotification(notification1)
// 			.darakbangId(darakbang.getId())
// 			.build());
//
// 		memberNotificationRepository.save(MemberNotification.builder()
// 			.memberId(darakbangMember.getMemberId())
// 			.moudaNotification(notification2)
// 			.darakbangId(darakbang.getId())
// 			.build());
//
// 		List<NotificationFindAllResponse> responses = notificationService.findAllMyNotifications(member,
// 				darakbang.getId())
// 			.notifications();
//
// 		assertThat(responses).satisfies(res -> {
// 			assertThat(res).hasSize(2);
// 			assertThat(res).extracting(NotificationFindAllResponse::message)
// 				.containsExactly(type2.createMessage("상돌"), type1.createMessage("테스트모임"));
// 			assertThat(res).extracting(NotificationFindAllResponse::type)
// 				.containsExactly(type2.toString(), type1.toString());
// 		});
// 	}
// }
