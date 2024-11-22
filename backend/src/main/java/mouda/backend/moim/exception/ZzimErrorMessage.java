package mouda.backend.moim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ZzimErrorMessage {

	MOIN_NOT_FOUND("모임이 존재하지 않습니다.");

	private final String message;
}
