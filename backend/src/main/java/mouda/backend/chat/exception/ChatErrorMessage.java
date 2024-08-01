package mouda.backend.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatErrorMessage {

	MOIM_NOT_FOUND("존재하지 않는 모임에 채팅할 수 없습니다."),
	;

	private final String message;
}
