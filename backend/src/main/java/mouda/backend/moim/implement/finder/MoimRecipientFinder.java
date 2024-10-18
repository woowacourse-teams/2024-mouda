package mouda.backend.moim.implement.finder;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.darakbangmember.infrastructure.DarakbangMemberRepository;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class MoimRecipientFinder {

	private final ChamyoRepository chamyoRepository;
	private final DarakbangMemberRepository darakbangMemberRepository;

	public List<Recipient> getMoimCreatedNotificationRecipients(long darakbangId, long authorId) {
		List<DarakbangMember> darakbangMembers = darakbangMemberRepository.findAllByDarakbangId(darakbangId);

		return darakbangMembers.stream()
			.filter(darakbangMember -> darakbangMember.getId() != authorId)
			.map(darakbangMember -> new Recipient(darakbangMember.getMemberId(), darakbangMember.getId()))
			.toList();
	}

	public List<Recipient> getMoimModifiedNotificationRecipients(long moimId) {
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moimId);

		return chamyos.stream()
			.filter(chamyo -> chamyo.getMoimRole() != MoimRole.MOIMER)
			.map(chamyo -> new Recipient(chamyo.getDarakbangMember().getMemberId(), chamyo.getDarakbangMember().getId()))
			.toList();
	}
}
