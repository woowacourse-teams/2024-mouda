package mouda.backend.please.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.please.domain.Please;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.infrastructure.PleaseRepository;

@Component
@RequiredArgsConstructor
public class PleaseFinder {

	private final PleaseRepository pleaseRepository;

	public Please find(Long pleaseId) {
		return pleaseRepository.findById(pleaseId)
			.orElseThrow(() -> new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND));
	}

	public List<Please> findPleasesDesc(Long darakbangId) {
		return pleaseRepository.findAllByDarakbangIdOrderByIdDesc(darakbangId);
	}
}
