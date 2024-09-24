package mouda.backend.notification.implement.notificationbuilder;

import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MoudaNotification;

public interface NotificationBuilderStrategy {
	MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember sender);
}
