package mouda.backend.moim.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.common.exception.MoudaException;

public class CommentException extends MoudaException {

	public CommentException(HttpStatus httpStatus, CommentErrorMessage commentErrorMessage) {
		super(httpStatus, commentErrorMessage.getMessage());
	}
}
