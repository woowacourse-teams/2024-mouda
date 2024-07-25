package mouda.backend.moim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoimErrorMessage {

	NOT_FOUND("모임이 존재하지 않습니다."),
	MAX_PEOPLE("모임 최대 인원 수를 초과합니다."),
	PAST_DATE_TIME("모임 날짜를 현재 시점 이후로 입력해주세요."),
	TITLE_NOT_EXIST("모임 제목을 입력해주세요."),
	TITLE_TOO_LONG("모임 제목을 조금 더 짧게 입력해주세요."),
	DATE_NOT_EXIST("모임 날짜를 입력해주세요."),
	TIME_NOT_EXIST("모임 시간을 입력해주세요."),
	PLACE_NOT_EXIST("모임 장소를 입력해주세요."),
	PLACE_TOO_LONG("모임 장소를 조금 더 짧게 입력해주세요."),
	MAX_PEOPLE_IS_POSITIVE("모임 최대 인원은 양수여야 합니다."),
	MAX_PEOPLE_TOO_MANY("모임 최대 인원을 조금 더 적게 입력해주세요."),
	AUTHOR_NICKNAME_NOT_EXIST("모임 생성자 닉네임을 입력해주세요."),
	AUTHOR_NICKNAME_TOO_LONG("모임 생성자 이름을 조금 더 짧게 입력해주세요."),
	DESCRIPTION_TOO_LONG("모임 설명을 조금 더 짧게 입력해주세요.");

	private final String message;
}
