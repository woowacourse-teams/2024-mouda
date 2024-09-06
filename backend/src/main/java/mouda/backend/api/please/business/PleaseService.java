package mouda.backend.api.please.business;

import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.please.Please;
import mouda.backend.api.please.exception.PleaseErrorMessage;
import mouda.backend.api.please.exception.PleaseException;
import mouda.backend.api.please.infrastructure.InterestRepository;
import mouda.backend.api.please.infrastructure.PleaseRepository;
import mouda.backend.core.dto.please.request.PleaseCreateRequest;
import mouda.backend.core.dto.please.response.PleaseFindAllResponse;
import mouda.backend.core.dto.please.response.PleaseFindAllResponses;

@Service
@Transactional
@RequiredArgsConstructor
public class PleaseService {

	private final PleaseRepository pleaseRepository;
	private final InterestRepository interestRepository;

	public Please createPlease(Long darakbangId, DarakbangMember darakbangMember,
		PleaseCreateRequest pleaseCreateRequest) {
		Please please = pleaseCreateRequest.toEntity(darakbangMember.getId(), darakbangId);

		return pleaseRepository.save(please);
	}

	public void deletePlease(Long darakbangId, Long pleaseId, DarakbangMember darakbangMember) {
		Please please = pleaseRepository.findById(pleaseId)
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));
		if (please.isNotInDarakbang(darakbangId)) {
			throw new PleaseException(HttpStatus.BAD_REQUEST, PleaseErrorMessage.PLEASE_NOT_IN_DARAKBANG);
		}
		if (please.isNotAuthor(darakbangMember.getId())) {
			throw new PleaseException(HttpStatus.FORBIDDEN, PleaseErrorMessage.NOT_ALLOWED_TO_DELETE);
		}

		pleaseRepository.deleteById(pleaseId);
	}

	public PleaseFindAllResponses findAllPlease(Long darakbangId, DarakbangMember darakbangMember) {
		List<Please> pleases = pleaseRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId);

		return new PleaseFindAllResponses(
			pleases.stream()
				.map(please -> {
					boolean isInterested = interestRepository
						.existsByDarakbangMemberIdAndPleaseId(darakbangMember.getId(), please.getId());
					long interestCount = interestRepository.countByPleaseId(please.getId());
					return PleaseFindAllResponse.toResponse(please, isInterested, interestCount);
				})
				.sorted(Comparator.comparing(PleaseFindAllResponse::interestCount)
					.reversed()
					.thenComparing(PleaseFindAllResponse::pleaseId))
				.toList());
	}
}
