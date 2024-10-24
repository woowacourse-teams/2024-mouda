package mouda.backend.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class ChamyoException extends MoudaException {

	public ChamyoException(HttpStatus httpStatus, ChamyoErrorMessage chamyoErrorMessage) {
		super(httpStatus, chamyoErrorMessage.getMessage());
	}
}
