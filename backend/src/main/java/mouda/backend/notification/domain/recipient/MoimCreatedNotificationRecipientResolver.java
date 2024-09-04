package mouda.backend.notification.domain.recipient;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOIM_CREATED)
public class MoimCreatedNotificationRecipientResolver extends NoneChatRecipientResolverStrategy {

	public MoimCreatedNotificationRecipientResolver(DarakbangMemberRepository darakbangMemberRepository,
		MemberNotificationRepository memberNotificationRepository,
		ChamyoRepository chamyoRepository) {
		super(darakbangMemberRepository, memberNotificationRepository, chamyoRepository);
	}

	@Override
	public List<Long> resolveRecipients(long darakbangId, MoudaNotification notification, Moim moim,
		DarakbangMember sender) {
		List<Long> recipientsIds = darakbangMemberRepository.findAllByDarakbangId(darakbangId).stream()
			.filter(member -> !Objects.equals(member.getId(), sender.getId()))
			.map(DarakbangMember::getMemberId)
			.toList();

		saveNotificationsForMembers(recipientsIds, darakbangId, notification);
		return recipientsIds;
	}
}
