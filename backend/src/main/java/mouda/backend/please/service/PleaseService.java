package mouda.backend.please.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.please.domain.Please;
import mouda.backend.please.dto.request.PleaseCreateRequest;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.PleaseRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PleaseService {

	private final PleaseRepository pleaseRepository;

	public Please createPlease(Member member, PleaseCreateRequest pleaseCreateRequest) {
		Please please = pleaseCreateRequest.toEntity(member.getId());

		return pleaseRepository.save(please);
	}

	public void deletePlease(Member member, Long pleaseId) {
		Please please = pleaseRepository.findById(pleaseId)
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND.getMessage()));

		if (please.isNotAuthor(member.getId())) {
			throw new PleaseException(HttpStatus.FORBIDDEN, PleaseErrorMessage.NOT_ALLOWED_TO_DELETE.getMessage());
		}

		pleaseRepository.deleteById(pleaseId);
	}
}
