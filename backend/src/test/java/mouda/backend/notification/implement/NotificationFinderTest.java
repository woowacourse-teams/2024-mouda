package mouda.backend.notification.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;

@SpringBootTest
class NotificationFinderTest extends DarakbangSetUp {

	@Autowired
	private NotificationFinder notificationFinder;

	@Autowired
	private MemberNotificationRepository memberNotificationRepository;

	@DisplayName("회원의 모든 알림을 조회한다.")
	@Test
	void findAllMemberNotification() {
		// given
		memberNotificationRepository.saveAll(createTestEntity(darakbangHogee, 10));

		// when
		List<MemberNotification> result = notificationFinder.findAllMemberNotification(darakbangHogee);

		// then
		assertThat(result).hasSize(10);
	}

	private List<MemberNotificationEntity> createTestEntity(DarakbangMember darakbangMember, int count) {
		return IntStream.rangeClosed(1, count)
			.mapToObj(i -> MemberNotificationEntity.builder()
				.darakbangMemberId(darakbangMember.getId())
				.title("title" + i)
				.body("body" + i)
				.createdAt(LocalDateTime.now())
				.type("MOIM_CREATED")
				.targeturl("targeturl" + i)
				.build())
			.toList();
	}
}
