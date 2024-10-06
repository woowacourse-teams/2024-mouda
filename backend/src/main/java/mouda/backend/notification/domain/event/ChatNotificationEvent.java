package mouda.backend.notification.domain.event;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.CommonNotification;

@Getter
public class ChatNotificationEvent extends NotificationEvent {

	private final List<DarakbangMember> members;
	private final long chatRoomId;

	@Builder
	public ChatNotificationEvent(CommonNotification notification, List<DarakbangMember> members, long chatRoomId) {
		super(notification);
		this.members = members;
		this.chatRoomId = chatRoomId;
	}
}
