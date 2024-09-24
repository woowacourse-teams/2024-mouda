package mouda.backend.notification.implement.notificationbuilder;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@RequiredArgsConstructor
public abstract class MoimNotificationBuilder implements NotificationBuilderStrategy {

	@Value("${url.base}")
	protected String baseUrl;

	@Value("${url.moim}")
	protected String moimUrl;

	protected final MoudaNotificationRepository moudaNotificationRepository;
}
