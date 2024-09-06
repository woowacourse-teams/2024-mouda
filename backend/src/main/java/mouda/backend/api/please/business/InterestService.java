package mouda.backend.api.please.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.please.Interest;
import mouda.backend.core.domain.please.Please;
import mouda.backend.api.please.exception.PleaseErrorMessage;
import mouda.backend.api.please.exception.PleaseException;
import mouda.backend.api.please.infrastructure.InterestRepository;
import mouda.backend.api.please.infrastructure.PleaseRepository;
import mouda.backend.core.dto.please.request.InterestUpdateRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

	private final PleaseRepository pleaseRepository;
	private final InterestRepository interestRepository;

	public void updateInterest(Long darakbangId, DarakbangMember darakbangMember, InterestUpdateRequest request) {
		Please please = pleaseRepository.findById(request.pleaseId())
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));
		if (please.isNotInDarakbang(darakbangId)) {
			throw new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.PLEASE_NOT_IN_DARAKBANG);
		}

		if (request.isInterested()) {
			addInterest(darakbangMember, request.pleaseId());
			return;
		}
		removeInterest(darakbangMember, request.pleaseId());
	}

	private void addInterest(DarakbangMember darakbangMember, Long pleaseId) {
		boolean isInterestExists = interestRepository.existsByDarakbangMemberIdAndPleaseId(darakbangMember.getId(),
			pleaseId);

		if (!isInterestExists) {
			Please please = pleaseRepository.findById(pleaseId)
				.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));
			Interest newInterest = Interest.builder()
				.darakbangMember(darakbangMember)
				.please(please)
				.build();
			interestRepository.save(newInterest);
		}
	}

	private void removeInterest(DarakbangMember darakbangMember, Long pleaseId) {
		interestRepository.findByDarakbangMemberIdAndPleaseId(darakbangMember.getId(), pleaseId)
			.ifPresent(interestRepository::delete);
	}
}
