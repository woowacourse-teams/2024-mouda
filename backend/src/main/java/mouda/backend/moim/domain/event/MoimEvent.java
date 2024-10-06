package mouda.backend.moim.domain.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@SuperBuilder
public class MoimEvent extends BaseNotificationEvent {

	private final String specificUrl;

	public MoimEvent(String baseUrl, String specificUrl, Moim moim, NotificationType notificationType) {
		super(baseUrl, moim, notificationType);
		this.specificUrl = specificUrl;
	}

	@Override
	public String getSpecificUrl() {
		return specificUrl;
	}
}
