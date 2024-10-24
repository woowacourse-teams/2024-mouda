package mouda.backend.please.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.please.domain.Please;
import mouda.backend.please.infrastructure.PleaseRepository;

@Component
@RequiredArgsConstructor
public class PleaseWriter {

	private final PleaseRepository pleaseRepository;

	public Please savePlease(Please please) {
		return pleaseRepository.save(please);
	}

	public void delete(Long pleaseId) {
		pleaseRepository.deleteById(pleaseId);
	}
}
