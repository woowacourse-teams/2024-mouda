package mouda.backend.moim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoimErrorMessage {

	NOT_FOUND("모임이 존재하지 않습니다."),
	MAX_PEOPLE("모임 최대 인원 수를 초과합니다."),
	;

	private final String message;
}
