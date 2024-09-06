package mouda.backend.api.auth.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class AuthException extends MoudaException {

	public AuthException(HttpStatus httpStatus, AuthErrorMessage authErrorMessage) {
		super(httpStatus, authErrorMessage.getMessage());
	}
}
