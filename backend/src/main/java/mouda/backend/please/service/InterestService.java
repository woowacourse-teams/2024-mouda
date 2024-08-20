package mouda.backend.please.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Interest;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.InterestUpdateRequest;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.InterestRepository;
import mouda.backend.please.repository.PleaseRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

	private final PleaseRepository pleaseRepository;
	private final InterestRepository interestRepository;

	public void updateInterest(Long darakbangId, DarakbangMember member, InterestUpdateRequest request) {
		Please please = pleaseRepository.findById(request.pleaseId())
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));
		if (please.inNotDarakbang(darakbangId)) {
			throw new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.PLEASE_NOT_IN_DARAKBANG);
		}

		if (request.isInterested()) {
			addInterest(member, request.pleaseId());
			return;
		}
		removeInterest(member, request.pleaseId());
	}

	private void addInterest(DarakbangMember member, Long pleaseId) {
		boolean isInterestExists = interestRepository.existsByMemberIdAndPleaseId(member.getId(), pleaseId);

		if (!isInterestExists) {
			Please please = pleaseRepository.findById(pleaseId)
				.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));
			Interest newInterest = Interest.builder()
				.member(member)
				.please(please)
				.build();
			interestRepository.save(newInterest);
		}
	}

	private void removeInterest(DarakbangMember member, Long pleaseId) {
		interestRepository.findByMemberIdAndPleaseId(member.getId(), pleaseId)
			.ifPresent(interestRepository::delete);
	}
}
