package mouda.backend.chat.domain;

import java.util.Objects;

import lombok.Getter;

@Getter
public class Participant {

	private final long darakbangMemberId;
	private final String nickname;
	private final String profile;
	private final String role;

	public Participant(long darakbangMemberId, String nickname, String profile, String role) {
		this.darakbangMemberId = darakbangMemberId;
		this.nickname = nickname;
		this.profile = profile;
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Participant that = (Participant)o;
		return darakbangMemberId == that.darakbangMemberId && Objects.equals(nickname, that.nickname)
			&& Objects.equals(profile, that.profile) && Objects.equals(role, that.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(darakbangMemberId, nickname, profile, role);
	}
}
