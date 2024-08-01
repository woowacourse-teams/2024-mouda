package mouda.backend.chamyo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChamyoErrorMessage {

	MOIM_NOT_FOUND("참여하려는 모임이 존재하지 않습니다."),
	MOIM_ALREADY_JOINED("이미 참여한 모임입니다."),
	MOIM_FULL("최대 인원 수를 초과했습니다."),
	MOIMING_CANCLED("취소된 모임입니다."),
	MOIMING_COMPLETE("모집이 완료된 모임입니다.");

	private final String message;
}
