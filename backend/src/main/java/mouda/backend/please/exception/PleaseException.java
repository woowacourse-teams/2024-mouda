package mouda.backend.please.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class PleaseException extends MoudaException {

	public PleaseException(HttpStatus httpStatus, PleaseErrorMessage message) {
		super(httpStatus, message.getMessage());
	}
}
