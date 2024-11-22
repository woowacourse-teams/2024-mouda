package mouda.backend.moim.implement.notificiation.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@RequiredArgsConstructor
@Builder
public class MoimCreateNotificationEvent {

	private final Moim moim;
	private final DarakbangMember host;
	private final NotificationType notificationType;
}
