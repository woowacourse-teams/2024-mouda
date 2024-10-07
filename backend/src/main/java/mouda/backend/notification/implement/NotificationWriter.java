package mouda.backend.notification.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;

@Component
@RequiredArgsConstructor
public class NotificationWriter {

	private final MemberNotificationRepository memberNotificationRepository;

	public void saveAllMemberNotification(CommonNotification notification, List<Recipient> recipients) {
		List<MemberNotificationEntity> memberNotifications = recipients.stream()
			.map(recipient -> createEntity(notification, recipient))
			.toList();

		memberNotificationRepository.saveAll(memberNotifications);
	}

	private MemberNotificationEntity createEntity(CommonNotification notification, Recipient recipient) {
		return MemberNotificationEntity.builder()
			.darakbangMemberId(recipient.getDarakbangMemberId())
			.type(notification.getType().name())
			.title(notification.getTitle())
			.body(notification.getBody())
			.targeturl(notification.getRedirectUrl())
			.build();
	}
}
