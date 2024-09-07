package mouda.backend.moim.implement.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.exception.MoimErrorMessage;
import mouda.backend.moim.exception.MoimException;
import mouda.backend.moim.infrastructure.MoimRepository;

@Component
@RequiredArgsConstructor
public class MoimValidator {

	private final MoimRepository moimRepository;

	public void validateMoimExists(long moimId, long darakbangId) {
		if (!moimRepository.existsByIdAndDarakbangId(moimId, darakbangId)) {
			throw new MoimException(HttpStatus.NOT_FOUND, MoimErrorMessage.NOT_FOUND);
		}
	}
}
