package mouda.backend.please.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Interest;
import mouda.backend.please.domain.Please;
import mouda.backend.please.infrastructure.InterestRepository;

@Component
@RequiredArgsConstructor
public class InterestWriter {

	private final InterestRepository interestRepository;
	private final InterestFinder interestFinder;
	private final PleaseFinder pleaseFinder;
	private final InterestValidator interestValidator;

	public void changeInterest(Please please, boolean isInterested, DarakbangMember darakbangMember) {
		if (isInterested) {
			Interest newInterest = Interest.builder()
				.darakbangMember(darakbangMember)
				.please(please)
				.build();
			interestRepository.save(newInterest);
			return;
		}
		removeInterest(darakbangMember.getMemberId(), please.getId());
	}

	private void removeInterest(Long darakbangMemberId, Long pleaseId) {
		interestFinder.findInterest(darakbangMemberId, pleaseId)
			.ifPresent(interestRepository::delete);
	}
}
