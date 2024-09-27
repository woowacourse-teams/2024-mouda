package mouda.backend.bet.business;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.domain.BetDetails;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.bet.implement.BetWriter;
import mouda.backend.bet.presentation.request.BetCreateRequest;
import mouda.backend.bet.presentation.response.BetFindAllResponses;
import mouda.backend.bet.presentation.response.BetFindResponse;
import mouda.backend.darakbangmember.domain.DarakbangMember;

@Service
@RequiredArgsConstructor
public class BetService {

	private final BetFinder betFinder;
	private final BetWriter betWriter;

	public BetFindAllResponses findAllBets(long darakbangId) {
		List<BetDetails> betDetails = betFinder.findAllDetails(darakbangId);
		return BetFindAllResponses.toResponse(betDetails);
	}

	public BetFindResponse findBet(long betId) {
		Bet bet = betFinder.find(betId);
		return BetFindResponse.toResponse(bet);
	}

	public long createBet(BetCreateRequest betRequest, DarakbangMember darakbangMember) {
		BetDetails betDetails = betRequest.toBetDetails();
		long betId = betWriter.save(betDetails);
		betWriter.participate(betId, darakbangMember);
		return betId;
	}

	public void participateBet(long betId, DarakbangMember darakbangMember) {
		betWriter.participate(betId, darakbangMember);
	}
}

