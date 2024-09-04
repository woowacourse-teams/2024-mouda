package mouda.backend.notification.domain.recipient;

import java.util.List;

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
@NotificationTypeProvider(NotificationType.NEW_MOIMEE_JOINED)
public class NewMoimeeJoinedNotificationRecipientResolver extends NoneChatRecipientResolverStrategy {

	public NewMoimeeJoinedNotificationRecipientResolver(
		DarakbangMemberRepository darakbangMemberRepository,
		MemberNotificationRepository memberNotificationRepository,
		ChamyoRepository chamyoRepository
	) {
		super(darakbangMemberRepository, memberNotificationRepository, chamyoRepository);
	}

	@Override
	public List<Long> resolveRecipients(long darakbangId, MoudaNotification notification, Moim moim,
		DarakbangMember sender) {

		List<Long> recipientIds = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.map(c -> c.getDarakbangMember().getMemberId())
			.filter(memberId -> !memberId.equals(sender.getMemberId()))
			.toList();

		saveNotificationsForMembers(recipientIds, darakbangId, notification);
		return recipientIds;
	}
}
