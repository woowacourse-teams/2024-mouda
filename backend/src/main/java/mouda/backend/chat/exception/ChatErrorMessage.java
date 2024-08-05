package mouda.backend.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatErrorMessage {

	MOIM_NOT_FOUND("존재하지 않는 모임에 채팅할 수 없습니다."),
	INVALID_RECENT_CHAT_ID("최근 조회된 채팅 아이디를 잘 못 입력하였습니다."),
	NOT_PARTICIPANT_OF_MOIM("모임에 참여한 회원만 채팅을 보낼 수 있습니다."),
	;

	private final String message;
}
