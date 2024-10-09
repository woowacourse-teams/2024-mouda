package mouda.backend.notification.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;

@Component
@RequiredArgsConstructor
public class NotificationFinder {

	private final MemberNotificationRepository memberNotificationRepository;

	public List<MemberNotification> findAllMemberNotification(DarakbangMember darakbangMember) {
		return memberNotificationRepository.findAllByDarakbangMemberId(darakbangMember.getId()).stream()
			.map(this::convertTo)
			.toList();
	}

	private MemberNotification convertTo(MemberNotificationEntity entity) {
		return MemberNotification.builder()
			.title(entity.getTitle())
			.message(entity.getBody())
			.createdAt(entity.getCreatedAt())
			.type(entity.getType())
			.redirectUrl(entity.getTargetUrl())
			.build();
	}
}
