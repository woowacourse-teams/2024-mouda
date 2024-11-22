package mouda.backend.bet.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class BetException extends MoudaException {

	public BetException(HttpStatus httpStatus, BetErrorMessage betErrorMessage) {
		super(httpStatus, betErrorMessage.getMessage());
	}
}
