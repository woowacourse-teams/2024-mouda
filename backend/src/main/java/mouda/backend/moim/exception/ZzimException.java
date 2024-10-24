package mouda.backend.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class ZzimException extends MoudaException {

	public ZzimException(HttpStatus httpStatus, ZzimErrorMessage zzimErrorMessage) {
		super(httpStatus, zzimErrorMessage.getMessage());
	}
}
