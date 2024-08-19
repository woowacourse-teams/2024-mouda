package mouda.backend.please.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.RequiredDarakbangPlease;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.PleaseCreateRequest;
import mouda.backend.please.dto.response.PleaseFindAllResponse;
import mouda.backend.please.dto.response.PleaseFindAllResponses;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.InterestRepository;
import mouda.backend.please.repository.PleaseRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PleaseService {

	private final PleaseRepository pleaseRepository;
	private final InterestRepository interestRepository;

	public Please createPlease(Long darakbangId, DarakbangMember member, PleaseCreateRequest pleaseCreateRequest) {
		Please please = pleaseCreateRequest.toEntity(member.getId(), darakbangId);

		return pleaseRepository.save(please);
	}

	@RequiredDarakbangPlease
	public void deletePlease(Long darakbangId, Long pleaseId, DarakbangMember member) {
		Please please = pleaseRepository.findById(pleaseId)
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));

		if (please.isNotAuthor(member.getId())) {
			throw new PleaseException(HttpStatus.FORBIDDEN, PleaseErrorMessage.NOT_ALLOWED_TO_DELETE);
		}

		pleaseRepository.deleteById(pleaseId);
	}

	public PleaseFindAllResponses findAllPlease(Long darakbangId, DarakbangMember member) {
		List<Please> pleases = pleaseRepository.findAllByDarakbangId(darakbangId);

		return new PleaseFindAllResponses(
			pleases.stream()
				.map(please -> {
					boolean isInterested = interestRepository
						.existsByMemberIdAndPleaseId(member.getId(), please.getId());
					long interestCount = interestRepository.countByPleaseId(please.getId());
					return PleaseFindAllResponse.toResponse(please, isInterested, interestCount);
				})
				.toList());
	}
}
