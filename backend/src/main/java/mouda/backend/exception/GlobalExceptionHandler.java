package mouda.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
		MissingServletRequestParameterException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponse(exception.getParameterName() + "은 NULL일 수 없습니다."));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		String error = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

		return ResponseEntity.badRequest().body(new ErrorResponse(error));
	}

	@ExceptionHandler(MoudaException.class)
	public ResponseEntity<ErrorResponse> handleMoudaException(MoudaException exception) {
		return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		exception.printStackTrace();
		return ResponseEntity.internalServerError().body(new ErrorResponse("서버 오류가 발생했습니다."));
	}
}
