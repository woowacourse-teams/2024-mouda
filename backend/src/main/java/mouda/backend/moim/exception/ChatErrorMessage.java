package mouda.backend.moim.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatErrorMessage {

	MOIM_NOT_FOUND("모임이 존재하지 않습니다."),
	INVALID_RECENT_CHAT_ID("최근 조회된 채팅 아이디를 잘 못 입력하였습니다."),
	NOT_PARTICIPANT_TO_SEND("모임에 참여한 회원만 채팅을 보낼 수 있습니다."),
	NOT_PARTICIPANT_TO_FIND("모임에 참여한 회원만 채팅을 조회할 수 있습니다."),
	MOIMER_CAN_CONFIRM_PLACE("모이머만 모임 장소를 확정할 수 있습니다."),
	MOIMER_CAN_CONFIRM_DATETIME("모이머만 날짜와 시간을 확정할 수 있습니다."),
	NO_PERMISSION_OPEN_CHAT("채팅은 모이머만 열 수 있습니다");

	private final String message;
}
