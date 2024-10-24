package mouda.backend.moim.implement.notificiation;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.UrlConfig;
import mouda.backend.notification.implement.NotificationProcessor;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(UrlConfig.class)
public abstract class AbstractMoimRelatedNotificationEventHandler {

	protected final UrlConfig urlConfig;
	protected final NotificationProcessor notificationProcessor;

	protected String getMoimUrl(long darakbangId, long moimId) {
		return urlConfig.getMoimUrl(darakbangId, moimId);
	}
}
