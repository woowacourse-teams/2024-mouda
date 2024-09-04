package mouda.backend.notification.domain.recipient;

import org.springframework.stereotype.Component;

import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.repository.MemberNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOINING_REOPENED)
public class MoimingReopenedNotificationRecipientResolver extends MoimStatusChangedNotificationRecipientResolver {
	
	public MoimingReopenedNotificationRecipientResolver(
		DarakbangMemberRepository darakbangMemberRepository,
		MemberNotificationRepository memberNotificationRepository,
		ChamyoRepository chamyoRepository) {
		super(darakbangMemberRepository, memberNotificationRepository, chamyoRepository);
	}
}
