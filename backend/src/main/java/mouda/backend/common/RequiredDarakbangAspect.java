package mouda.backend.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.repository.MoimRepository;
import mouda.backend.please.exception.PleaseErrorMessage;
import mouda.backend.please.exception.PleaseException;
import mouda.backend.please.repository.PleaseRepository;

@Aspect
@Component
@RequiredArgsConstructor
public class RequiredDarakbangAspect {

	private final MoimRepository moimRepository;
	private final PleaseRepository pleaseRepository;

	@Before(value = "@annotation(RequiredDarakbangMoim) && args(darakbangId, moimId,..)")
	public void validateDarakbangMoim(Long darakbangId, Long moimId) {
		if (!moimRepository.existsByIdAndDarakbangId(moimId, darakbangId)) {
			throw new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND);
		}
	}

	@Before(value = "@annotation(RequiredDarakbangPlease) && args(darakbangId, pleaseId,..)")
	public void validateDarakbangPlease(Long darakbangId, Long pleaseId) {
		if (!pleaseRepository.existsByIdAndDarakbangId(pleaseId, darakbangId)) {
			throw new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND);
		}
	}
}
