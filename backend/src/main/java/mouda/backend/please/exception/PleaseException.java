package mouda.backend.please.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.exception.MoudaException;

public class PleaseException extends MoudaException {

	public PleaseException(HttpStatus httpStatus, String message) {
		super(httpStatus, message);
	}
}
