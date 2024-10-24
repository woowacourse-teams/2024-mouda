package mouda.backend.notification.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class FcmToken {

	private final long memberId;
	private final long tokenId;
	private final String token;

	@Override
	public String toString() {
		return "FcmToken{" +
			"memberId=" + memberId +
			", tokenId=" + tokenId +
			'}';
	}
}
