package mouda.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Author {

	private final long id;
	private final String nickname;
	private final String profile;

	@Builder
	public Author(long id, String nickname, String profile) {
		this.id = id;
		this.nickname = nickname;
		this.profile = profile;
	}
}
