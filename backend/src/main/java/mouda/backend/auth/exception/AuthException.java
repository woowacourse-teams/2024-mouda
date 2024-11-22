package mouda.backend.auth.exception;

import mouda.backend.common.exception.MoudaException;
import org.springframework.http.HttpStatus;

public class AuthException extends MoudaException {

    public AuthException(HttpStatus httpStatus, AuthErrorMessage authErrorMessage) {
        super(httpStatus, authErrorMessage.getMessage());
    }
}
