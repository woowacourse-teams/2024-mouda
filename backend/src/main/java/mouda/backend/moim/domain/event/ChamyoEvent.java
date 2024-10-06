package mouda.backend.moim.domain.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@SuperBuilder
public class ChamyoEvent extends BaseNotificationEvent {

	private final DarakbangMember updatedMember;
	private final String specificUrl;

	public ChamyoEvent(String baseUrl, String moimUrlFormat, Moim moim, NotificationType notificationType, DarakbangMember updatedMember,
		String specificUrl) {
		super(baseUrl, moim, notificationType);
		this.updatedMember = updatedMember;
		this.specificUrl = specificUrl;

	}

	@Override
	public String getSpecificUrl() {
		return specificUrl;
	}
}
