package mouda.backend.api.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.api.common.exception.MoudaException;

public class CommentException extends MoudaException {

	public CommentException(HttpStatus httpStatus, CommentErrorMessage commentErrorMessage) {
		super(httpStatus, commentErrorMessage.getMessage());
	}
}
