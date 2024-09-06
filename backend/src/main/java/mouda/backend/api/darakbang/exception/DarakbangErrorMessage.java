package mouda.backend.api.darakbang.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DarakbangErrorMessage {

	NAME_ALREADY_EXIST("이미 존재하는 다락방 이름입니다."),
	NAME_NOT_EXIST("다락방 이름이 존재하지 않습니다."),
	INVALID_CODE("유효하지 않은 초대코드입니다."),
	CODE_ALREADY_EXIST("이미 존재하는 초대코드입니다."),
	DARAKBANG_NOT_FOUND("다락방이 존재하지 않습니다."),
	;

	private final String message;
}
