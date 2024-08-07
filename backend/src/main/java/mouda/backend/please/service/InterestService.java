package mouda.backend.please.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.please.domain.Interest;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.InterestUpdateRequest;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.InterestRepository;
import mouda.backend.please.repository.PleaseRepository;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final PleaseRepository pleaseRepository;
	private final InterestRepository interestRepository;

	public void updateInterest(Member member, InterestUpdateRequest request) {
		if (request.isInterested()) {
			addInterest(member, request.pleaseId());
			return;
		}
		removeInterest(member, request.pleaseId());
	}

	private void addInterest(Member member, Long pleaseId) {
		boolean isInterestExists = interestRepository.findByMemberIdAndPleaseId(member.getId(), pleaseId).isPresent();

		if (!isInterestExists) {
			Please please = pleaseRepository.findById(pleaseId)
				.orElseThrow(() -> new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.NOT_FOUND));
			Interest newInterest = Interest.builder()
				.member(member)
				.please(please)
				.build();
			interestRepository.save(newInterest);
		}
	}

	private void removeInterest(Member member, Long pleaseId) {
		interestRepository.findByMemberIdAndPleaseId(member.getId(), pleaseId)
			.ifPresent(interestRepository::delete);
	}

}
