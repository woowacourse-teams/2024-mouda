package mouda.backend.moim.implement.notificiation.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@RequiredArgsConstructor
@Builder
public class MoimEditedNotificationEvent {

	private final Moim moim;
	private final String oldMoimTitle;
	private final NotificationType notificationType;
}
