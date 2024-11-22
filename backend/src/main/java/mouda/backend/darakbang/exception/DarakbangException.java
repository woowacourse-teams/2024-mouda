package mouda.backend.darakbang.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class DarakbangException extends MoudaException {

	public DarakbangException(HttpStatus httpStatus, DarakbangErrorMessage message) {
		super(httpStatus, message.getMessage());
	}
}
