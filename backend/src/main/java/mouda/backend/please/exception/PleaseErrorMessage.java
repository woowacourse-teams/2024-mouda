package mouda.backend.please.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PleaseErrorMessage {

	NOT_FOUND("해주세요가 존재하지 않습니다."),
	NOT_ALLOWED_TO_DELETE("삭제 권한이 없습니다.");

	private final String message;
}
