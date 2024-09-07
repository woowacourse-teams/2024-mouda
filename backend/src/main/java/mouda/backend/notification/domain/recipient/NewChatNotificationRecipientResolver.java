package mouda.backend.notification.domain.recipient;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;

@Component
@NotificationTypeProvider(NotificationType.NEW_CHAT)
public class NewChatNotificationRecipientResolver implements RecipientResolverStrategy {

	private final ChamyoRepository chamyoRepository;

	public NewChatNotificationRecipientResolver(ChamyoRepository chamyoRepository) {
		this.chamyoRepository = chamyoRepository;
	}

	@Override
	public List<Long> resolveRecipients(long darakbangId, MoudaNotification notification, Moim moim,
		DarakbangMember sender) {

		return chamyoRepository.findAllByMoimId(moim.getId()).stream()
			.map(chamyo -> chamyo.getDarakbangMember().getMemberId())
			.filter(memberId -> !Objects.equals(memberId, sender.getMemberId()))
			.toList();
	}
}
