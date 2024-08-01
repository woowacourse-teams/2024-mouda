package mouda.backend.zzim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ZzimErrorMessage {

	MOIN_NOT_FOUND("찜할 모임을 찾을 수 없습니다.");

	private final String message;
}
