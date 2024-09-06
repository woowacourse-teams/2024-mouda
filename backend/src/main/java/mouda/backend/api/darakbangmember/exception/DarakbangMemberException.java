package mouda.backend.api.darakbangmember.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class DarakbangMemberException extends MoudaException {

	public DarakbangMemberException(HttpStatus httpStatus, DarakbangMemberErrorMessage message) {
		super(httpStatus, message.getMessage());
	}
}
