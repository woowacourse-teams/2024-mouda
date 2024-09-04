package mouda.backend.notification.domain.recipient;

import java.util.List;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;

@RequiredArgsConstructor
public abstract class NoneChatRecipientResolverStrategy implements RecipientResolverStrategy {

	protected final DarakbangMemberRepository darakbangMemberRepository;
	protected final MemberNotificationRepository memberNotificationRepository;
	protected final ChamyoRepository chamyoRepository;

	public void saveNotificationsForMembers(List<Long> recipientsIds, long darakbangId,
		MoudaNotification notification) {
		memberNotificationRepository.saveAll(recipientsIds.stream()
			.map(memberId -> MemberNotification.builder()
				.memberId(memberId)
				.darakbangId(darakbangId)
				.moudaNotification(notification)
				.build())
			.toList());
	}
}
