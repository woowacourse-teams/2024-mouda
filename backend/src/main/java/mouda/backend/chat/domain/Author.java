package mouda.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Author {

	private final long darakbangMemberId;
	private final long memberId;
	private final String nickname;
	private final String profile;

	@Builder
	public Author(long darakbangMemberId, long memberId, String nickname, String profile) {
		this.darakbangMemberId = darakbangMemberId;
		this.memberId = memberId;
		this.nickname = nickname;
		this.profile = profile;
	}

	public boolean isNotSameMember(long darakbangMemberId, long memberId) {
		return this.darakbangMemberId != darakbangMemberId || this.memberId != memberId;
	}
}
