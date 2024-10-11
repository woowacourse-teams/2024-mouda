package mouda.backend.chat.domain;

import lombok.Getter;

@Getter
public class Loser {

	private String nickname;
	private String profile;
	private String role;

	public Loser(String nickname, String profile, String role) {
		this.nickname = nickname;
		this.profile = profile;
		this.role = role;
	}
}
