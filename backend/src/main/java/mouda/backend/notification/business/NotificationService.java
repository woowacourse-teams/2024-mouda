package mouda.backend.notification.business;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.event.ChatNotificationEvent;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.event.MoimCreateNotificationEvent;
import mouda.backend.notification.domain.event.MultipleMemberNotificationEvent;
import mouda.backend.notification.domain.event.SingleMemberNotificationEvent;
import mouda.backend.notification.implement.NotificationWriter;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationWriter notificationWriter;
	private final ApplicationEventPublisher eventPublisher;

	@TransactionalEventListener
	public void sendNotification(CommonNotification notification, DarakbangMember darakbangMember) {
		notificationWriter.saveMemberNotification(notification, darakbangMember);

		SingleMemberNotificationEvent event = SingleMemberNotificationEvent.builder()
			.notification(notification)
			.darakbangMember(darakbangMember)
			.build();

		eventPublisher.publishEvent(event);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void sendNotification(CommonNotification notification, List<DarakbangMember> darakbangMembers) {
		notificationWriter.saveAllMemberNotification(notification, darakbangMembers);

		MultipleMemberNotificationEvent event = MultipleMemberNotificationEvent.builder()
			.notification(notification)
			.members(darakbangMembers)
			.build();

		eventPublisher.publishEvent(event);
	}

	@Transactional
	public void sendMoimCreateNotification(CommonNotification notification, List<DarakbangMember> darakbangMembers) {
		notificationWriter.saveAllMemberNotification(notification, darakbangMembers);

		MoimCreateNotificationEvent event = MoimCreateNotificationEvent.builder()
			.notification(notification)
			.members(darakbangMembers)
			.build();

		eventPublisher.publishEvent(event);
	}

	public void sendChatNotification(CommonNotification notification, List<DarakbangMember> members, long chatRoomId) {
		ChatNotificationEvent event = ChatNotificationEvent.builder()
			.notification(notification)
			.members(members)
			.chatRoomId(chatRoomId)
			.build();

		eventPublisher.publishEvent(event);
	}
}

