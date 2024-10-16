package mouda.backend.moim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoimErrorMessage {

	NOT_FOUND("모임이 존재하지 않습니다."),
	PAST_DATE_TIME("모임 날짜를 현재 시점 이후로 입력해주세요."),
	TITLE_NOT_EXIST("모임 제목을 입력해주세요."),
	TITLE_TOO_LONG("모임 제목을 조금 더 짧게 입력해주세요."),
	PLACE_NOT_EXIST("모임 장소를 입력해주세요."),
	PLACE_TOO_LONG("모임 장소를 조금 더 짧게 입력해주세요."),
	MAX_PEOPLE_IS_POSITIVE("모임 최대 인원은 양수여야 합니다."),
	MAX_PEOPLE_TOO_MANY("모임 최대 인원을 조금 더 적게 입력해주세요."),
	DESCRIPTION_TOO_LONG("모임 설명을 조금 더 짧게 입력해주세요."),
	NOT_ALLOWED_TO_COMPLETE("방장만 완료할 수 있어요."),
	MOIM_CANCELED("이미 취소된 모임이에요."),
	ALREADY_COMPLETED("이미 모집 완료된 모임이에요."),
	NOT_ALLOWED_TO_CANCEL("방장만 취소할 수 있어요."),
	NOT_ALLOWED_TO_REOPEN("방장만 다시 열 수 있어요."),
	MOIM_FULL_FOR_REOPEN("모임이 꽉 차서 다시 열 수 없어요."),
	ALREADY_MOIMING("이미 모집 중인 모임이에요."),
	NOT_ALLOWED_TO_EDIT("방장만 수정할 수 있어요."),
	MAX_PEOPLE_IS_LOWER_THAN_CURRENT_PEOPLE("모임 최대 인원을 현재 인원보다 작게 설정할 수 없어요.");

	private final String message;
}
