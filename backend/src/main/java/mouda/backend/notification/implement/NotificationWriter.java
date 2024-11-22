package mouda.backend.notification.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import mouda.backend.notification.infrastructure.repository.MemberNotificationRepository;

@Component
@RequiredArgsConstructor
public class NotificationWriter {

	private final MemberNotificationRepository memberNotificationRepository;

	public void saveMemberNotification(NotificationPayload notificationPayload) {
		CommonNotification notification = notificationPayload.toCommonNotification();
		List<Recipient> recipients = notificationPayload.getRecipients();

		if (notification.getType() == NotificationType.NEW_CHAT) {
			return;
		}

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
			.createdAt(notification.getCreatedAt())
			.build();
	}
}
