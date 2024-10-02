// package mouda.backend.notification.implement;
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
// import mouda.backend.common.fixture.MemberFixture;
// import mouda.backend.member.domain.Member;
// import mouda.backend.member.infrastructure.MemberRepository;
// import mouda.backend.notification.domain.MemberNotification;
// import mouda.backend.notification.domain.MoudaNotification;
// import mouda.backend.notification.domain.NotificationType;
// import mouda.backend.notification.infrastructure.MemberNotificationRepository;
// import mouda.backend.notification.infrastructure.MoudaNotificationRepository;
//
// @SpringBootTest
// public class MemberNotificationFinderTest {
//
// 	@Autowired
// 	MemberNotificationRepository memberNotificationRepository;
//
// 	@Autowired
// 	MoudaNotificationRepository moudaNotificationRepository;
//
// 	@Autowired
// 	MemberRepository memberRepository;
//
// 	@Autowired
// 	private MemberNotificationFinder memberNotificationFinder;
//
// 	@Test
// 	@DisplayName("Member와 DarakbangId에 해당하는 알림 목록을 역순으로 반환한다.")
// 	void findAllMemberNotifications_success() {
// 		// given
// 		Member member = MemberFixture.getTebah();
// 		Member savedMember = memberRepository.save(member);
//
// 		long darakbangId = 1L;
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
// 		MemberNotification memberNotification1 = MemberNotification.builder()
// 			.memberId(savedMember.getId())
// 			.moudaNotification(notification1)
// 			.darakbangId(darakbangId)
// 			.build();
//
// 		MemberNotification memberNotification2 = MemberNotification.builder()
// 			.memberId(savedMember.getId())
// 			.moudaNotification(notification2)
// 			.darakbangId(darakbangId)
// 			.build();
//
// 		memberNotificationRepository.save(memberNotification1);
// 		memberNotificationRepository.save(memberNotification2);
//
// 		// when
// 		List<MemberNotification> actualNotifications = memberNotificationFinder.findAll(member, darakbangId);
//
// 		// then
// 		assertThat(actualNotifications).hasSize(2);
// 		assertThat(actualNotifications)
// 			.extracting("id")
// 			.containsExactly(memberNotification2.getId(), memberNotification1.getId());
// 	}
// }
