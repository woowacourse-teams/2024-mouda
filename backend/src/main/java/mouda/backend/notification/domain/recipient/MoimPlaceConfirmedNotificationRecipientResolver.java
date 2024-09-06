package mouda.backend.notification.domain.recipient;

import java.util.List;

import org.springframework.stereotype.Component;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.api.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.core.domain.moim.MoimRole;
import mouda.backend.api.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.MOIM_PLACE_CONFIRMED)
public class MoimPlaceConfirmedNotificationRecipientResolver extends NoneChatRecipientResolverStrategy {
	public MoimPlaceConfirmedNotificationRecipientResolver(
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
