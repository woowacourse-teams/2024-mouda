package mouda.backend.please.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.please.domain.Please;
import mouda.backend.please.infrastructure.InterestRepository;
import mouda.backend.please.infrastructure.PleaseRepository;

@Component
@RequiredArgsConstructor
public class InterestValidator {

	private final InterestRepository interestRepository;
	private final PleaseRepository pleaseRepository;
	private final PleaseValidator pleaseValidator;

	public void validate(Please please, Long darakbangId) {
		pleaseValidator.validateNotInDarakbang(please, darakbangId);
	}
}
