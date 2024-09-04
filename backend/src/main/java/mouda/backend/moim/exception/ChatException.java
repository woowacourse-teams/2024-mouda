package mouda.backend.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class ChatException extends MoudaException {

	public ChatException(HttpStatus httpStatus, ChatErrorMessage chatErrorMessage) {
		super(httpStatus, chatErrorMessage.getMessage());
	}
}
