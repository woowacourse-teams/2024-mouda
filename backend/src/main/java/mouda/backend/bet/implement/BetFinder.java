package mouda.backend.bet.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.domain.Loser;
import mouda.backend.bet.domain.Participant;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.exception.BetErrorMessage;
import mouda.backend.bet.exception.BetException;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@Transactional
@RequiredArgsConstructor
public class BetFinder {

	private final BetDarakbangMemberRepository betDarakbangMemberRepository;
	private final BetRepository betRepository;
	private final ParticipantFinder participantFinder;

	public Bet find(long darakbangId, long betEntityId) {
		BetEntity betEntity = betRepository.findByIdAndDarakbangId(betEntityId, darakbangId)
			.orElseThrow(() -> new BetException(HttpStatus.NOT_FOUND, BetErrorMessage.BET_NOT_FOUND));
		List<Participant> participants = participantFinder.findAllByBetEntity(betEntity);

		return Bet.builder()
			.betDetails(betEntity.toBetDetails())
			.moimerId(betEntity.getMoimerId())
			.participants(participants)
			.loserId(betEntity.getLoserDarakbangMemberId())
			.build();
	}

	public List<Bet> findAllByDarakbangId(long darakbangId) {
		List<BetEntity> betEntities = betRepository.findAllByDarakbangId(darakbangId);

		return createBets(betEntities);
	}

	public List<Bet> findAllDrawableBet() {
		List<BetEntity> betEntities = betRepository.findAllByBettingTimeAndLoserDarakbangMemberIdIsNull(
			LocalDateTime.now().withSecond(0).withNano(0));

		return createBets(betEntities);
	}

	private List<Bet> createBets(List<BetEntity> betEntities) {
		return betEntities.stream()
			.map(this::createBet)
			.toList();
	}

	private Bet createBet(BetEntity betEntity) {
		List<Participant> participants = participantFinder.findAllByBetEntity(betEntity);
		return Bet.builder()
			.betDetails(betEntity.toBetDetails())
			.moimerId(betEntity.getMoimerId())
			.loserId(betEntity.getLoserDarakbangMemberId())
			.darakbangId(betEntity.getDarakbangId())
			.participants(participants)
			.build();
	}

	@Transactional(readOnly = true)
	public Loser findResult(long darakbangId, long betId) {
		BetEntity betEntity = betRepository.findByIdAndDarakbangId(betId, darakbangId)
			.orElseThrow(() -> new BetException(HttpStatus.NOT_FOUND, BetErrorMessage.BET_NOT_FOUND));

		Long loserDarakbangMemberId = betEntity.getLoserDarakbangMemberId();
		if (loserDarakbangMemberId == null) {
			throw new BetException(HttpStatus.NOT_FOUND, BetErrorMessage.LOSER_NOT_FOUND);
		}

		BetDarakbangMemberEntity betDarakbangMemberEntity = betDarakbangMemberRepository
			.findByBetIdAndDarakbangMemberId(betId, loserDarakbangMemberId)
			.orElseThrow(() -> new BetException(HttpStatus.NOT_FOUND, BetErrorMessage.BET_DARAKBANG_MEMBER_NOT_FOUND));

		return new Loser(betDarakbangMemberEntity.getDarakbangMember().getId(),
			betDarakbangMemberEntity.getDarakbangMember().getNickname());
	}

	public List<BetDetails> readAllMyBets(DarakbangMember darakbangMember) {
		return betDarakbangMemberRepository.findAllByDarakbangMemberId(darakbangMember.getId()).stream()
			.map(BetDarakbangMemberEntity::getBet)
			.map(BetEntity::toBetDetails)
			.toList();
	}
}
