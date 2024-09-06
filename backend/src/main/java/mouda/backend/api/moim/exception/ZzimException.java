package mouda.backend.api.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class ZzimException extends MoudaException {

	public ZzimException(HttpStatus httpStatus, ZzimErrorMessage zzimErrorMessage) {
		super(httpStatus, zzimErrorMessage.getMessage());
	}
}
