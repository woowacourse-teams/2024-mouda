package mouda.backend.zzim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ZzimErrorMessage {

	MOIN_NOT_FOUND("찜할 모임을 찾을 수 없습니다."),
	MOIM_NOT_IN_DARAKBANG("다락방에 존재하는 모임이 아닙니다.");

	private final String message;
}
