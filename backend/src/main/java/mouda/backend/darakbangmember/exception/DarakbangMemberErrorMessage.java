package mouda.backend.darakbangmember.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DarakbangMemberErrorMessage {

	NICKNAME_NOT_EXIST("닉네임이 존재하지 않습니다."),
	;

	private final String message;
}
