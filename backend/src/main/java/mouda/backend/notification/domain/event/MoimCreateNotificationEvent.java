package mouda.backend.notification.domain.event;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.CommonNotification;

@Getter
public class MoimCreateNotificationEvent extends NotificationEvent {

	private final List<DarakbangMember> members;

	@Builder
	public MoimCreateNotificationEvent(CommonNotification notification, List<DarakbangMember> members) {
		super(notification);
		this.members = members;
	}
}
