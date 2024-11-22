package mouda.backend.please.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;
import mouda.backend.please.domain.PleaseWithInterest;
import mouda.backend.please.domain.PleaseWithInterests;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.infrastructure.PleaseRepository;

@Component
@RequiredArgsConstructor
public class PleaseFinder {

	private final PleaseRepository pleaseRepository;
	private final InterestFinder interestFinder;
	private final InterestValidator interestValidator;

	public Please find(Long pleaseId, long darakbangId) {
		Please please = pleaseRepository.findById(pleaseId)
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));

		interestValidator.validate(please, darakbangId);
		return please;
	}

	public PleaseWithInterests findPleasesDesc(Long darakbangId, DarakbangMember darakbangMember) {
		List<Please> pleases = pleaseRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId);

		List<PleaseWithInterest> pleaseWithInterests = pleases.stream()
			.map(please -> getPleaseWithInterest(darakbangMember, please))
			.sorted()
			.toList();

		return new PleaseWithInterests(pleaseWithInterests);
	}

	private PleaseWithInterest getPleaseWithInterest(DarakbangMember darakbangMember, Please please) {
		boolean isInterested = interestFinder.findInterest(darakbangMember.getId(), please.getId())
			.isPresent();
		long interestCount = interestFinder.countInterest(please);
		return new PleaseWithInterest(please, isInterested, interestCount);
	}
}
