package mouda.backend.notification.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.implement.NotificationFinder;
import mouda.backend.notification.presentation.response.MemberNotificationFindAllResponse;

@Service
@RequiredArgsConstructor
public class MemberNotificationService {

	private final NotificationFinder notificationFinder;

	@Transactional(readOnly = true)
	public MemberNotificationFindAllResponse findAllMemberNotification(DarakbangMember darakbangMember) {
		List<MemberNotification> notifications = notificationFinder.findAllMemberNotification(darakbangMember);

		return MemberNotificationFindAllResponse.from(notifications);
	}
}
