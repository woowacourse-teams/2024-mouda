package mouda.backend.comment.exception;

import org.springframework.http.HttpStatus;

import mouda.backend.exception.MoudaException;

public class CommentException extends MoudaException {
	
	public CommentException(HttpStatus httpStatus, CommentErrorMessage commentErrorMessage) {
		super(httpStatus, commentErrorMessage.getMessage());
	}
}
