package mouda.backend.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class MoimException extends MoudaException {

	public MoimException(HttpStatus httpStatus, MoimErrorMessage moimErrorMessage) {
		super(httpStatus, moimErrorMessage.getMessage());
	}
}
