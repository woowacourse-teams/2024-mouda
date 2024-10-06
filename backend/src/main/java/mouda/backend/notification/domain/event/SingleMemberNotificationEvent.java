package mouda.backend.notification.domain.event;

import lombok.Builder;
import lombok.Getter;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.CommonNotification;

@Getter
public class SingleMemberNotificationEvent extends NotificationEvent {

	private final DarakbangMember darakbangMember;

	@Builder
	public SingleMemberNotificationEvent(CommonNotification notification, DarakbangMember darakbangMember) {
		super(notification);
		this.darakbangMember = darakbangMember;
	}
}
