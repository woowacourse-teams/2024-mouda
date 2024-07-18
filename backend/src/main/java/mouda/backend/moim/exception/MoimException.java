package mouda.backend.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.exception.ServiceException;

public class MoimException extends ServiceException {

	public MoimException(HttpStatus httpStatus, MoimErrorMessage moimErrorMessage) {
		super(httpStatus, moimErrorMessage.getMessage());
	}
}
