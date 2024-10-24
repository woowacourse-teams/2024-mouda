package mouda.backend.member.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class MemberException extends MoudaException {

	public MemberException(HttpStatus httpStatus, MemberErrorMessage chatErrorMessage) {
		super(httpStatus, chatErrorMessage.getMessage());
	}
}
