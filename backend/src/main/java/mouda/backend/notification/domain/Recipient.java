package mouda.backend.notification.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class Recipient {

	private final long memberId;
	private final long darakbangMemberId;
}
