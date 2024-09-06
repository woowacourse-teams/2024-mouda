package mouda.backend.api.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class MoimException extends MoudaException {

	public MoimException(HttpStatus httpStatus, MoimErrorMessage moimErrorMessage) {
		super(httpStatus, moimErrorMessage.getMessage());
	}
}
