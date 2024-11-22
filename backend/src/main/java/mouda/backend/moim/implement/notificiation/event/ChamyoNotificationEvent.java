package mouda.backend.moim.implement.notificiation.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.NotificationType;

@Getter
@RequiredArgsConstructor
@Builder
public class ChamyoNotificationEvent {

	private final Chamyo chamyo;
	private final NotificationType notificationType;

	public Moim getMoim() {
		return chamyo.getMoim();
	}

	public DarakbangMember getUpdatedMember() {
		return chamyo.getDarakbangMember();
	}
}
