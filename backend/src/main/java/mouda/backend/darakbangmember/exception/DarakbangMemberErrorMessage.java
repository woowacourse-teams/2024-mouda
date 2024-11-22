package mouda.backend.darakbangmember.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DarakbangMemberErrorMessage {

	NICKNAME_NOT_EXIST("닉네임이 존재하지 않습니다."),
	INVALID_LENGTH("닉네임은 9글자 이하로만 가능합니다."),
	NICKNAME_ALREADY_EXIST("이미 존재하는 닉네임입니다."),
	MEMBER_ALREADY_EXIST("이미 가입한 멤버입니다."),
	MEMBER_NOT_EXIST("존재하지 않는 다락방 멤버입니다."),
	NOT_ALLOWED_TO_READ("조회 권한이 없습니다."),
	INVALID_DELETE_FILE("기존 이미지를 삭제할 수 없습니다."),
	INVALID_FILE("잘못된 이미지 파일입니다.");

	private final String message;
}
