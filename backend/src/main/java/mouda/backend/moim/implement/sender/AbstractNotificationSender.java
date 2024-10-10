package mouda.backend.moim.implement.sender;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.common.config.UrlConfig;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(UrlConfig.class)
public abstract class AbstractNotificationSender {

	private final UrlConfig urlConfig;

	protected String getChatRoomUrl(long darakbangId, long moimId) {
		return urlConfig.getChatRoomUrl(darakbangId, moimId);
	}

	protected String getMoimUrl(long darakbangId, long moimId) {
		return urlConfig.getMoimUrl(darakbangId, moimId);
	}
}
