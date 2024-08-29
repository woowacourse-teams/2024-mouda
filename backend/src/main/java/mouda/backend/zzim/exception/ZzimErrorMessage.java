package mouda.backend.zzim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ZzimErrorMessage {

	// 404
	MOIN_NOT_FOUND("모임이 존재하지 않아요!");

	private final String message;
}
