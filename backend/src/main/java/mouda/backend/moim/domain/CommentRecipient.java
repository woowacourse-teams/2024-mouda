package mouda.backend.moim.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@RequiredArgsConstructor
@Getter
public class CommentRecipient {

	private final NotificationType notificationType;
	private final List<Recipient> recipients;
}
