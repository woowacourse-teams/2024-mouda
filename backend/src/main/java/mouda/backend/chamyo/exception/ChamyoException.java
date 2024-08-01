package mouda.backend.chamyo.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.exception.MoudaException;

public class ChamyoException extends MoudaException {

	public ChamyoException(HttpStatus httpStatus, ChamyoErrorMessage chamyoErrorMessage) {
		super(httpStatus, chamyoErrorMessage.getMessage());
	}
}
