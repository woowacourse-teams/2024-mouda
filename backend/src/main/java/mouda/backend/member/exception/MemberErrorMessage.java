package mouda.backend.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorMessage {

	MEMBER_NOT_FOUND("회원이 존재하지 않습니다.");

	private final String message;
}
