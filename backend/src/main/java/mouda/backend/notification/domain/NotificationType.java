package mouda.backend.notification.domain;

import java.util.function.Function;

public enum NotificationType {

	// 전체 전송
	// MoimEvent -> 방장을 제외한 사람들한테 알림
	MOIM_CREATED(moimName -> moimName + " 모임이 만들어졌어요!"),

	// 방장 제외 참여자
	MOIMING_COMPLETED(moimName -> moimName + " 모집이 마감되었어요!"),
	MOINING_REOPENED(moimName -> moimName + " 모집이 재개되었어요!"),
	MOIM_CANCELLED(moimName -> moimName + " 모임이 취소되었어요!"),
	MOIM_MODIFIED(moimName -> moimName + " 모임 정보가 변경되었어요!"),
	MOIM_PLACE_CONFIRMED(moimName -> moimName + " 모임 장소가 확정되었어요!"),
	MOIM_TIME_CONFIRMED(moimName -> moimName + " 모임 시간이 확정되었어요!"),

	// ChamyoEvent -> 참여자(나간사람)를 제외한 나머지 모든 모임 참여자
	NEW_MOIMEE_JOINED(memberName -> memberName + " 님이 모임에 참여했어요!"),
	MOIMEE_LEFT(memberName -> memberName + " 님이 참여를 취소했어요!"),

	// 댓글 쓴사람 제외 참여자
	// 댓글
	NEW_COMMENT(memberName -> memberName + " 님이 댓글을 남겼어요!"),
	NEW_REPLY(memberName -> memberName + " 님이 답글을 남겼어요!"),

	// 채팅방 참여자
	// 채팅
	NEW_CHAT(memberName -> memberName + " 님이 메시지를 보냈어요!"),
	;

	private final Function<String, String> messageFunction;

	NotificationType(Function<String, String> messageFunction) {
		this.messageFunction = messageFunction;
	}

	public String createMessage(String prefix) {
		return messageFunction.apply(prefix);
	}

	public boolean isConfirmedType() {
		return this == MOIM_PLACE_CONFIRMED || this == MOIM_TIME_CONFIRMED;
	}
}
