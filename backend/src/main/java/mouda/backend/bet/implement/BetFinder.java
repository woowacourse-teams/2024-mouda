package mouda.backend.bet.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.domain.Participant;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetFinder {

	private final BetDarakbangMemberRepository betDarakbangMemberRepository;
	private final BetRepository betRepository;

	public Bet find(long betEntityId) {
		BetEntity betEntity = betRepository.findById(betEntityId).orElseThrow(IllegalArgumentException::new);
		List<BetDarakbangMemberEntity> betDarakbangMemberEntities = betDarakbangMemberRepository.findAllByBetId(betEntity.getId());
		List<Participant> participants = betDarakbangMemberEntities.stream()
			.map(betDarakbangMemberEntity -> betDarakbangMemberEntity.getDarakbangMember().toParticipant())
			.toList();

		return Bet.builder()
			.betDetails(betEntity.toBetDetails())
			.participants(participants)
			.build();
	}

	public List<BetDetails> findAllDetails() {
		List<BetEntity> betEntities = betRepository.findAll();
		return betEntities.stream()
			.map(BetEntity::toBetDetails)
			.toList();
	}

	public List<Bet> findAll() {
		List<BetEntity> betEntities = betRepository.findAllByBettingTime(LocalDateTime.now().withSecond(0).withNano(0));

		return betEntities.stream()
			.map(betEntity -> {
				List<DarakbangMember> entities = betDarakbangMemberRepository.findAllDarakbangMemberByBetId(betEntity.getId());
				List<Participant> participants = entities.stream().map(DarakbangMember::toParticipant).toList();
				return Bet.builder()
					.betDetails(betEntity.toBetDetails())
					.participants(participants)
					.build();
			}).toList();
	}
}
