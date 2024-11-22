package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.BetRole;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Participant;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetParticipantResolver implements ParticipantsResolver {

	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	@Override
	public boolean support(ChatRoomType chatRoomType) {
		return chatRoomType == ChatRoomType.BET;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Participant> resolve(ChatRoom chatRoom) {
		return betDarakbangMemberRepository.findAllByBetId(chatRoom.getTargetId()).stream()
			.map(betDarakbangMemberEntity -> {
				DarakbangMember darakbangMember = betDarakbangMemberEntity.getDarakbangMember();
				BetEntity bet = betDarakbangMemberEntity.getBet();
				return new Participant(
					darakbangMember.getId(),
					darakbangMember.getNickname(),
					darakbangMember.getProfile(),
					getBetRole(darakbangMember, bet).toString());
			})
			.toList();
	}

	private BetRole getBetRole(DarakbangMember darakbangMember, BetEntity betEntity) {
		return betEntity.getMoimerId() == darakbangMember.getId() ? BetRole.MOIMER : BetRole.MOIMEE;
	}
}
