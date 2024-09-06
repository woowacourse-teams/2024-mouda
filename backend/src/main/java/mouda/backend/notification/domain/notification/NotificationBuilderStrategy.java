package mouda.backend.notification.domain.notification;

import mouda.backend.core.domain.darakbang.DarakbangMember;
import mouda.backend.core.domain.moim.Moim;
import mouda.backend.notification.domain.MoudaNotification;

public interface NotificationBuilderStrategy {
	MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember sender);
}
