package mouda.backend.notification.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.CommonNotification;

@RequiredArgsConstructor
@Getter
public abstract class NotificationEvent {

	protected final CommonNotification notification;
}
