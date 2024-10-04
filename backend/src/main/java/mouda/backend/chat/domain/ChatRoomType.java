package mouda.backend.chat.domain;

public enum ChatRoomType {
	MOIM, BET;

	public boolean isNotMoim() {
		return this != MOIM;
	}
}
