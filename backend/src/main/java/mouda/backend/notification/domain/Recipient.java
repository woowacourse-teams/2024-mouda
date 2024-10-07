package mouda.backend.notification.domain;

import lombok.Getter;

@Getter
public class Recipient {

	private long memberId;
	private long darakbangMemberId;

	public Recipient(long memberId, long darakbangMemberId) {
		this.memberId = memberId;
		this.darakbangMemberId = darakbangMemberId;
	}
}
