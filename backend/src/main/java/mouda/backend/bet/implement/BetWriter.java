package mouda.backend.bet.implement;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BettingResult;
import mouda.backend.bet.domain.Loser;
import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Component
@RequiredArgsConstructor
public class BetWriter {

	private final BetRepository betRepository;
	private final BetDarakbangMemberRepository betDarakbangMemberRepository;

	public void saveAll(BettingResult bettingResult) {
		Map<Bet, Loser> results = bettingResult.getResults();
		List<BetEntity> betEntities = results.keySet().stream()
			.map(key -> BetEntity.of(key, results.get(key)))
			.toList();

		betRepository.saveAll(betEntities);
	}

	public long save(long darakbangId, Bet bet) {
		BetEntity betEntity = betRepository.save(BetEntity.create(bet, darakbangId));
		return betEntity.getId();
	}

	public void participate(long darakbangId, long betId, DarakbangMember darakbangMember) {
		BetEntity betEntity = betRepository.findByIdAndDarakbangId(betId, darakbangId)
			.orElseThrow(() -> new IllegalArgumentException("no bet"));

		BetDarakbangMemberEntity betDarakbangMemberEntity = new BetDarakbangMemberEntity(darakbangMember, betEntity);
		betDarakbangMemberRepository.save(betDarakbangMemberEntity);
	}
}
