package mouda.backend.bet.implement;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.exception.BetErrorMessage;
import mouda.backend.bet.exception.BetException;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetWriter {

	private final BetRepository betRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;
	private final BetFinder betFinder;

	public void saveAll(List<Bet> bets) {
		List<BetEntity> betEntities = bets.stream()
			.map(BetEntity::from)
			.toList();

		betRepository.saveAll(betEntities);
	}

	public long save(long darakbangId, Bet bet) {
		BetEntity betEntity = betRepository.save(BetEntity.create(bet, darakbangId));
		return betEntity.getId();
	}

	public void participate(long darakbangId, long betId, DarakbangMember darakbangMember) {
		Bet bet = betFinder.find(darakbangId, betId);
		participate(darakbangMember, bet);
	}

	private void participate(DarakbangMember darakbangMember, Bet bet) {
		BetEntity betEntity = BetEntity.from(bet);
		BetDarakbangMemberEntity betDarakbangMemberEntity = new BetDarakbangMemberEntity(darakbangMember, betEntity);
		try {
			betDarakbangMemberRepository.save(betDarakbangMemberEntity);
		} catch (DataIntegrityViolationException e) {
			throw new BetException(HttpStatus.BAD_REQUEST, BetErrorMessage.ALREADY_PARTICIPATED_BET);
		}
	}

	public void updateLoser(Bet bet) {
		betRepository.save(BetEntity.from(bet));
	}
}
