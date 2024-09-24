package mouda.backend.moim.domain;

import java.time.LocalDateTime;

public class EmptyChat extends Chat {

	private Long moimId;

	public EmptyChat(long moimId) {
		super();
		this.moimId = moimId;
	}

	public Long getMoimId() {
		return moimId;
	}

	@Override
	public String getContent() {
		return "";
	}

	@Override
	public LocalDateTime getDateTime() {
		return null;
	}
}
