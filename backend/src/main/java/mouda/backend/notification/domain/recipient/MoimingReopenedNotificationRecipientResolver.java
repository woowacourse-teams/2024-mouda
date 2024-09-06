package mouda.backend.notification.domain.recipient;

import org.springframework.stereotype.Component;

import mouda.backend.api.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.api.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;

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
