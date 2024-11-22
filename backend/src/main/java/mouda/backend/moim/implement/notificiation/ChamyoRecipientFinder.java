package mouda.backend.moim.implement.notificiation;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class ChamyoRecipientFinder {

	private final ChamyoRepository chamyoRepository;

	public List<Recipient> getChamyoNotificationRecipients(Moim moim, DarakbangMember updatedMember) {
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moim.getId());
		return chamyos.stream()
			.filter(chamyo -> chamyo.isNotSameMember(updatedMember))
			.map(Chamyo::getDarakbangMember)
			.map(darakbangMember -> Recipient.builder()
				.darakbangMemberId(darakbangMember.getId())
				.memberId(darakbangMember.getMemberId())
				.build()
			)
			.toList();
	}
}
