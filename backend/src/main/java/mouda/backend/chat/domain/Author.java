package mouda.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Author {

	private final long id;
	private final long memberId;
	private final String nickname;
	private final String profile;

	@Builder
	public Author(long id, long memberId, String nickname, String profile) {
		this.id = id;
		this.memberId = memberId;
		this.nickname = nickname;
		this.profile = profile;
	}
}
