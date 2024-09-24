package mouda.backend.please.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.please.domain.Interest;
import mouda.backend.please.domain.Please;
import mouda.backend.please.infrastructure.InterestRepository;

@Component
@RequiredArgsConstructor
public class InterestFinder {

	private final InterestRepository interestRepository;

	public Optional<Interest> findInterest(Long darakbangMemberId, Long pleaseId) {
		return interestRepository.findByDarakbangMemberIdAndPleaseId(darakbangMemberId, pleaseId);
	}

	public long countInterest(Please please) {
		return interestRepository.countByPleaseId(please.getId());
	}
}
