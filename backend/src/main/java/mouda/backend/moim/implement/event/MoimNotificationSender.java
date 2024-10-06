package mouda.backend.moim.implement.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Comment;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.implement.NotificationWriter;
import mouda.backend.notification.implement.subscription.SubscriptionFinder;

// todo: 멤버 필터링 & 알림 저장 & 전송 이벤트 발행 코드 작성
@Component
@RequiredArgsConstructor
public class MoimNotificationSender {

	private final ApplicationEventPublisher eventPublisher;
	private final NotificationWriter notificationWriter;
	private final SubscriptionFinder subscriptionFinder;
	private final MoimEventFactory moimEventFactory;

	public void sendMoimNotification(Moim moim, NotificationType notificationType) {

	}

	public void sendChamyoNotification(Chamyo chamyo, DarakbangMember updatedMember, NotificationType notificationType) {

	}

	public void sendCommentNotification(Comment comment, DarakbangMember author, NotificationType notificationType) {

	}
}
