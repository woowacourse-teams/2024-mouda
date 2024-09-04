package mouda.backend.notification.domain.recipient;

import java.util.List;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;

public abstract class MoimStatusChangedNotificationRecipientResolver extends NoneChatRecipientResolverStrategy {

	public MoimStatusChangedNotificationRecipientResolver(
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
			.filter(chamyo -> chamyo.getMoimRole() != MoimRole.MOIMER)
			.map(chamyo -> chamyo.getDarakbangMember().getMemberId())
			.toList();

		saveNotificationsForMembers(recipientIds, darakbangId, notification);
		return recipientIds;
	}
}
