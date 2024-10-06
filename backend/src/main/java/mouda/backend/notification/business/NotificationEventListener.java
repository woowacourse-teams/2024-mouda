package mouda.backend.notification.business;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.event.ChatNotificationEvent;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.event.MoimCreateNotificationEvent;
import mouda.backend.notification.domain.event.MultipleMemberNotificationEvent;
import mouda.backend.notification.domain.event.SingleMemberNotificationEvent;
import mouda.backend.notification.implement.NotificationSender;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

	private final NotificationSender notificationSender;

	@TransactionalEventListener(classes = SingleMemberNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(SingleMemberNotificationEvent singleMemberNotificationEvent) {
		CommonNotification notification = singleMemberNotificationEvent.getNotification();
		DarakbangMember darakbangMember = singleMemberNotificationEvent.getDarakbangMember();

		notificationSender.sendNotification(notification, darakbangMember);
	}

	@TransactionalEventListener(classes = MultipleMemberNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(MultipleMemberNotificationEvent multipleMemberNotificationEvent) {
		CommonNotification notification = multipleMemberNotificationEvent.getNotification();
		List<DarakbangMember> darakbangMembers = multipleMemberNotificationEvent.getMembers();

		notificationSender.sendNotification(notification, darakbangMembers);
	}

	@TransactionalEventListener(classes = MoimCreateNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void handle(MoimCreateNotificationEvent moimCreateNotificationEvent) {
		CommonNotification notification = moimCreateNotificationEvent.getNotification();
		List<DarakbangMember> darakbangMembers = moimCreateNotificationEvent.getMembers();

		notificationSender.sendMoimCreateNotification(notification, darakbangMembers);
	}

	@EventListener(classes = ChatNotificationEvent.class)
	public void handle(ChatNotificationEvent chatNotificationEvent) {
		CommonNotification notification = chatNotificationEvent.getNotification();
		List<DarakbangMember> darakbangMembers = chatNotificationEvent.getMembers();
		long chatRoomId = chatNotificationEvent.getChatRoomId();

		notificationSender.sendChatNotification(notification, darakbangMembers, chatRoomId);
	}
}
