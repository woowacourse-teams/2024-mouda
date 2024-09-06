package mouda.backend.api.please.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class PleaseException extends MoudaException {

	public PleaseException(HttpStatus httpStatus, PleaseErrorMessage message) {
		super(httpStatus, message.getMessage());
	}
}
