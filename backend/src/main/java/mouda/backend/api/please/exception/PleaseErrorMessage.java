package mouda.backend.api.please.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PleaseErrorMessage {

	NOT_FOUND("해주세요가 존재하지 않습니다."),
	TITLE_NOT_EXIST("제목이 존재하지 않습니다."),
	DESCRIPTION_NOT_EXIST("내용이 존재하지 않습니다."),
	NOT_ALLOWED_TO_DELETE("삭제 권한이 없습니다."),
	MEMBER_NOT_FOUND("멤버가 없습니다."),
	PLEASE_NOT_IN_DARAKBANG("다락방에 존재하는 해주세요가 아닙니다.");

	private final String message;
}
