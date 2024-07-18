package mouda.backend.moim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoimErrorMessage {

	MOIM_NOT_FOUND("모임이 존재하지 않습니다."),
	MOIM_MAX_PEOPLE("모임 최대 인원 수를 초과합니다."),
	;

	private final String message;
}
