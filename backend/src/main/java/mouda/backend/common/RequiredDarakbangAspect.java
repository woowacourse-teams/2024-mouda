package mouda.backend.common;

import java.lang.reflect.Parameter;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
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

	private static final List<String> REQUIRED_MOIM_PARAMETER_NAMES = List.of("darakbangId", "moimId");
	private static final List<String> REQUIRED_PLEASE_PARAMETER_NAMES = List.of("darakbangId", "pleaseId");

	private final MoimRepository moimRepository;
	private final PleaseRepository pleaseRepository;

	@Before(value = "@annotation(RequiredDarakbangMoim) && args(darakbangId, moimId,..)")
	public void validateDarakbangMoim(JoinPoint joinPoint, Long darakbangId, Long moimId) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Parameter[] parameters = signature.getMethod().getParameters();
		for (int i = 0; i < REQUIRED_MOIM_PARAMETER_NAMES.size(); i++) {
			assert REQUIRED_MOIM_PARAMETER_NAMES.get(i).equals(parameters[i].getName())
				: "올바른 파라미터 순서는 darakbangId, moimId 입니다.";
		}
		if (!moimRepository.existsByIdAndDarakbangId(moimId, darakbangId)) {
			throw new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND);
		}
	}

	@Before(value = "@annotation(RequiredDarakbangPlease) && args(darakbangId, pleaseId,..)")
	public void validateDarakbangPlease(JoinPoint joinPoint, Long darakbangId, Long pleaseId) {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Parameter[] parameters = signature.getMethod().getParameters();
		for (int i = 0; i < REQUIRED_PLEASE_PARAMETER_NAMES.size(); i++) {
			assert REQUIRED_PLEASE_PARAMETER_NAMES.get(i).equals(parameters[i].getName())
				: "올바른 파라미터 순서는 darakbangId, pleaseId 입니다.";
		}
		if (!pleaseRepository.existsByIdAndDarakbangId(pleaseId, darakbangId)) {
			throw new PleaseException(HttpStatus.NOT_FOUND, PleaseErrorMessage.NOT_FOUND);
		}
	}
}
