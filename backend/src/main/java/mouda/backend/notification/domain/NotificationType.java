package mouda.backend.notification.domain;

public enum NotificationType {

	MOIM_CREATED,

	MOIMING_COMPLETED,
	MOINING_REOPENED,
	MOIM_CANCELLED,
	MOIM_MODIFIED,
	MOIM_PLACE_CONFIRMED,
	MOIM_TIME_CONFIRMED,

	NEW_MOIMEE_JOINED,
	MOIMEE_LEFT,

	NEW_COMMENT,
	NEW_REPLY,

	NEW_CHAT,
	;

	public boolean isConfirmedType() {
		return this == MOIM_PLACE_CONFIRMED || this == MOIM_TIME_CONFIRMED;
	}

	public boolean isChatType() {
		return this == NEW_CHAT || isConfirmedType();
	}
}
