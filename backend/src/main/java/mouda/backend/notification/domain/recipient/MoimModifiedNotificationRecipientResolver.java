package mouda.backend.notification.domain.recipient;

import org.springframework.stereotype.Component;

import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.repository.MemberNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOIM_MODIFIED)
public class MoimModifiedNotificationRecipientResolver extends MoimStatusChangedNotificationRecipientResolver {

	public MoimModifiedNotificationRecipientResolver(
		DarakbangMemberRepository darakbangMemberRepository,
		MemberNotificationRepository memberNotificationRepository,
		ChamyoRepository chamyoRepository) {
		super(darakbangMemberRepository, memberNotificationRepository, chamyoRepository);
	}
}
