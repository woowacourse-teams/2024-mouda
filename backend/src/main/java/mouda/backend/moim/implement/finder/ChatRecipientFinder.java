package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class ChatRecipientFinder {

	private final ChamyoRepository chamyoRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	public List<Recipient> getMoimChatNotificationRecipients(long moimId, DarakbangMember sender) {
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moimId);

		Stream<DarakbangMember> darakbangMemberStream = chamyos.stream()
			.map(Chamyo::getDarakbangMember);

		return getNotificationRecipients(darakbangMemberStream, sender);
	}

	public List<Recipient> getBetChatNotificationRecipients(long betId, DarakbangMember sender) {
		List<BetDarakbangMemberEntity> members = betDarakbangMemberRepository.findAllByDarakbangMemberId(
			betId);

		Stream<DarakbangMember> darakbangMemberStream = members.stream()
			.map(BetDarakbangMemberEntity::getDarakbangMember);

		return getNotificationRecipients(darakbangMemberStream, sender);
	}

	public List<Recipient> getNotificationRecipients(Stream<DarakbangMember> memberStream, DarakbangMember sender) {
		return memberStream
			.filter(darakbangMember -> darakbangMember.isNotSameMemberWith(sender))
			.map(darakbangMember -> new Recipient(darakbangMember.getMemberId(), darakbangMember.getId()))
			.toList();
	}
}
