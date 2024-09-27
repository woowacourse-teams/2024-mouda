package mouda.backend.bet.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Participant;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class ParticipantFinder {

	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	public List<Participant> findAllByBetEntity(BetEntity betEntity) {
		List<BetDarakbangMemberEntity> betDarakbangMemberEntities = betDarakbangMemberRepository.findAllByBetId(betEntity.getId());
		return betDarakbangMemberEntities.stream()
			.map(betDarakbangMemberEntity -> {
				DarakbangMember darakbangMember = betDarakbangMemberEntity.getDarakbangMember();
				return new Participant(darakbangMember.getId(), darakbangMember.getNickname());
			})
			.toList();
	}
}
