package mouda.backend.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorMessage {

    UNAUTHORIZED("인증되지 않은 사용자입니다.");

    private final String message;
}
