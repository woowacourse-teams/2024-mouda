package mouda.backend.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorMessage {

    UNAUTHORIZED("모임이 존재하지 않습니다.");

    private final String message;
}
