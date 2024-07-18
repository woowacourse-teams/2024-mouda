package mouda.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(ServiceException e) {
		return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getMessage()));
	}
}
