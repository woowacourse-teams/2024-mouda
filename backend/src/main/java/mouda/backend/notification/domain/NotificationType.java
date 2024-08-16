package mouda.backend.notification.domain;

import java.util.function.Function;

public enum NotificationType {

	// 모든 다락방 내 회원에게 전달
	MOIM_CREATED(moimName -> moimName + " 모임이 만들어졌어요!"),

	// 해당되는 회원에게만 전달
	MOIMING_COMPLETED(moimName -> moimName + " 모임이 마감되었어요!"),
	MOIM_CANCELLED(moimName -> moimName + " 모임이 취소되었어요!"),
	MOIM_MODIFIED(moimName -> moimName + " 모임 정보가 변경되었어요!"),
	NEW_COMMENT(memberName -> memberName + " 님이 댓글을 남겼어요!"),
	NEW_REPLY(memberName -> memberName + " 님이 답글을 남겼어요!"),
	NEW_CHAT(memberName -> memberName + " 님이 메시지를 보냈어요!"),
	NEW_MOIMEE_JOINED(memberName -> memberName + " 님이 모임에 참여했어요!"),
	MOIMEE_LEFT(memberName -> memberName + " 님이 참여를 취소했어요!");


	private final Function<String, String> messageFunction;

	NotificationType(Function<String, String> messageFunction) {
		this.messageFunction = messageFunction;
	}

	public String createMessage(String moimName) {
		return messageFunction.apply(moimName);
	}
}
