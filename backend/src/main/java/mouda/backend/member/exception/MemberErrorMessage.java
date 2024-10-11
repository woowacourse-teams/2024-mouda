package mouda.backend.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorMessage {

	MEMBER_NAME_NOT_EXISTS("모임 참여자 이름을 입력해주세요."),
	;

	private final String message;
}
