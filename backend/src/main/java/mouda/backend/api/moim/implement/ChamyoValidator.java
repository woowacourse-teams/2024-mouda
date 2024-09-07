package mouda.backend.api.moim.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.api.moim.exception.ChamyoErrorMessage;
import mouda.backend.api.moim.exception.ChamyoException;
import mouda.backend.core.domain.moim.Moim;

@Component
@RequiredArgsConstructor
public class ChamyoValidator {

	public void validateInDarakbang(Moim moim, long darakbangId) {
		if (moim.isNotInDarakbang(darakbangId)) {
			throw new ChamyoException(HttpStatus.BAD_REQUEST, ChamyoErrorMessage.MOIM_NOT_FOUND);
		}
	}
}
