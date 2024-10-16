package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class ChatRecipientFinder {

	private final ChamyoRepository chamyoRepository;

	public List<Recipient> getChatNotificationRecipients(long moimId, DarakbangMember darakbangMember) {
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moimId);

		return chamyos.stream()
			.filter(chamyo -> !Objects.equals(chamyo.getDarakbangMember().getId(), darakbangMember.getId()))
			.map(chamyo -> new Recipient(chamyo.getDarakbangMember().getMemberId(), chamyo.getDarakbangMember().getId()))
			.toList();
	}
}
