package mouda.backend.darakbangmember.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.exception.MoudaException;

public class DarakbangMemberException extends MoudaException {

	public DarakbangMemberException(HttpStatus httpStatus, DarakbangMemberErrorMessage message) {
		super(httpStatus, message.getMessage());
	}
}
