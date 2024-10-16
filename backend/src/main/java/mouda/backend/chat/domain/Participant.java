package mouda.backend.chat.domain;

import java.util.Objects;

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

	public Participant(Chat chat) {
		this.nickname = chat.getDarakbangMember().getNickname();
		this.profile = chat.getDarakbangMember().getProfile();
		this.role = chat.getDarakbangMember().getDescription();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Participant that = (Participant)o;
		return Objects.equals(nickname, that.nickname) && Objects.equals(profile, that.profile) && Objects.equals(role,
			that.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nickname, profile, role);
	}
}
