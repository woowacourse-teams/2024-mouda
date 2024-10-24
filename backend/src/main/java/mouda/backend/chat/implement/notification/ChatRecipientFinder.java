package mouda.backend.chat.implement.notification;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.Author;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.notification.domain.Recipient;

@Component
@RequiredArgsConstructor
public class ChatRecipientFinder {

	private final ChamyoRepository chamyoRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	public List<Recipient> getMoimChatNotificationRecipients(long moimId, Author author) {
		List<Chamyo> chamyos = chamyoRepository.findAllByMoimId(moimId);

		Stream<DarakbangMember> darakbangMemberStream = chamyos.stream()
			.map(Chamyo::getDarakbangMember);

		return getNotificationRecipients(darakbangMemberStream, author);
	}

	public List<Recipient> getBetChatNotificationRecipients(long betId, Author author) {
		List<BetDarakbangMemberEntity> members = betDarakbangMemberRepository.findAllByBetId(betId);

		Stream<DarakbangMember> darakbangMemberStream = members.stream()
			.map(BetDarakbangMemberEntity::getDarakbangMember);

		return getNotificationRecipients(darakbangMemberStream, author);
	}

	public List<Recipient> getNotificationRecipients(Stream<DarakbangMember> memberStream, Author author) {
		return memberStream
			.filter(darakbangMember -> author.isNotSameMember(darakbangMember.getId(), darakbangMember.getMemberId()))
			.map(darakbangMember -> Recipient.builder()
				.memberId(darakbangMember.getMemberId())
				.darakbangMemberId(darakbangMember.getId())
				.build())
			.toList();
	}
}
