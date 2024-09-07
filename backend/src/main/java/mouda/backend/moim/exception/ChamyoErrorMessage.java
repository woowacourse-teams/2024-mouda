package mouda.backend.moim.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChamyoErrorMessage {

	MOIM_NOT_FOUND("모임이 존재하지 않습니다."),
	MOIM_ALREADY_JOINED("이미 참여했어요!"),
	MOIM_FULL("모임이 꽉 찼어요!"),
	MOIMING_CANCLED("모임이 취소됐어요!"),
	MOIMING_COMPLETE("모집이 완료됐어요!"),
	MOIM_NOT_JOINED("아직 참여하지 않았어요!"),
	CANNOT_CANCEL_CHAMYO("취소할 수 없어요!");

	private final String message;
}
