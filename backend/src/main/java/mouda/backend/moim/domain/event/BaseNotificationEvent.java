package mouda.backend.moim.domain.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@SuperBuilder
public abstract class BaseNotificationEvent {

	protected final String baseUrl;
	protected final Moim moim;
	protected final NotificationType notificationType;

	protected BaseNotificationEvent(String baseUrl, Moim moim, NotificationType notificationType) {
		this.baseUrl = baseUrl;
		this.moim = moim;
		this.notificationType = notificationType;
	}

	public String getRedirectUrl(long darakbangId, long moimId) {
		return baseUrl + String.format(getSpecificUrl(), darakbangId, moimId);
	}

	public abstract String getSpecificUrl();
}
