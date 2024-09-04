package mouda.backend.notification.domain.recipient;

import java.util.List;

import org.springframework.stereotype.Component;

import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.repository.repository.DarakbangMemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.repository.MemberNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOIM_TIME_CONFIRMED)
public class MoimTimeConfirmedNotificationRecipientResolver extends NoneChatRecipientResolverStrategy {

	public MoimTimeConfirmedNotificationRecipientResolver(
		DarakbangMemberRepository darakbangMemberRepository,
		MemberNotificationRepository memberNotificationRepository,
		ChamyoRepository chamyoRepository) {
		super(darakbangMemberRepository, memberNotificationRepository, chamyoRepository);
	}

	@Override
	public List<Long> resolveRecipients(long darakbangId, MoudaNotification notification, Moim moim,
		DarakbangMember sender) {
		List<Long> recipientIds = chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.filter(chamyo -> chamyo.getMoimRole() != MoimRole.MOIMER)
			.map(chamyo -> chamyo.getDarakbangMember().getMemberId())
			.toList();

		saveNotificationsForMembers(recipientIds, darakbangId, notification);
		return recipientIds;
	}
}
