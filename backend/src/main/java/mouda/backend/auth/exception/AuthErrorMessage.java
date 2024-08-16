package mouda.backend.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorMessage {

	UNAUTHORIZED("인증되지 않은 사용자입니다."),
	KAKAO_UNAUTHORIZED("카카오 인증에 실패하였습니다."),
	INVALID_KAKO_AUTH_CODE("유효하지 않은 인증 코드입니다."),
	KAKO_TOKEN_ISSUE_FAILED("토큰 발급에 실패하였습니다."),
	KAKAO_VALIDATION_FAILED("카카오 토큰 검증에 실패하였습니다."),
	MISSING_AUTH_CODE("인증 코드가 누락되었습니다."),
	INVALID_KAKAO_TOKEN("유효하지 않은 토큰 입니다."),
	EXPIRED_TOKEN("만료된 토큰입니다.");

	private final String message;
}
