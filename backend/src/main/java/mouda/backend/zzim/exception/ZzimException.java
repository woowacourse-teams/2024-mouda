package mouda.backend.zzim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.exception.MoudaException;

public class ZzimException extends MoudaException {

	public ZzimException(HttpStatus httpStatus, ZzimErrorMessage zzimErrorMessage) {
		super(httpStatus, zzimErrorMessage.getMessage());
	}
}
