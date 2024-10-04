package mouda.backend.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthErrorMessage {

	UNAUTHORIZED("인증되지 않은 사용자입니다."),
	KAKAO_UNAUTHORIZED("카카오 인증에 실패하였습니다."),
	INVALID_KAKO_AUTH_CODE("유효하지 않은 인증 코드입니다."),
	TOKEN_ISSUE_FAILED("토큰 발급에 실패하였습니다."),
	KAKAO_VALIDATION_FAILED("카카오 토큰 검증에 실패하였습니다."),
	MISSING_AUTH_CODE("인증 코드가 누락되었습니다."),
	INVALID_TOKEN("유효하지 않은 토큰 입니다."),
	EXPIRED_TOKEN("만료된 토큰입니다."),
	KAKAO_CONNECT_TIMEOUT("커넥션 타임아웃 되었습니다."),
	DARAKBANG_NOT_ENTERED("가입한 다락방이 아닙니다."),
	MEMBER_NOT_FOUND("회원가입 이력을 찾을 수 없습니다."),
	KAKAO_CANNOT_SIGNUP("기존 카카오 로그인 이력이 있는 사용자만 이용할 수 있는 서비스입니다. 새로운 회원은 다른 로그인 서비스를 이용해주세요.");

	private final String message;
}
