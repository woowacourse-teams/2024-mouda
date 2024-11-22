package mouda.backend.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoudaException extends RuntimeException {

	private HttpStatus httpStatus;
	private String message;
}
