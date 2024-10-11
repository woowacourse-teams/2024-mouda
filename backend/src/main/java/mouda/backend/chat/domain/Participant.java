package mouda.backend.chat.domain;

import lombok.Getter;

@Getter
public class Participant {
	
	private final String nickname;
	private final String profile;
	private final String role;

	public Participant(String nickname, String profile, String role) {
		this.nickname = nickname;
		this.profile = profile;
		this.role = role;
	}
}
