package mouda.backend.notification.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberNotification {

	private final String title;
	private final String message;
	private final LocalDateTime createdAt;
	private final String type;
	private final String redirectUrl;

}
