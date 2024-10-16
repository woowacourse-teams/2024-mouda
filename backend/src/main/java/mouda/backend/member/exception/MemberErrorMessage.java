package mouda.backend.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorMessage {

	MEMBER_NAME_NOT_EXISTS("멤버 이름이 존재하지 않습니다."),
	;

	private final String message;
}
