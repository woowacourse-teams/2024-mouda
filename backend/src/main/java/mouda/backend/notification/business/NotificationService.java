package mouda.backend.notification.business;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.FirebaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.implement.FcmClient;
import mouda.backend.notification.implement.FcmTokenFinder;
import mouda.backend.notification.implement.MemberNotificationFinder;
import mouda.backend.notification.implement.NotificationFactory;
import mouda.backend.notification.implement.RecipientFactory;
import mouda.backend.notification.presentation.request.FcmTokenSaveRequest;
import mouda.backend.notification.presentation.response.NotificationFindAllResponse;
import mouda.backend.notification.presentation.response.NotificationFindAllResponses;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = FirebaseException.class)
public class NotificationService {

	private final NotificationFactory notificationFactory;
	private final RecipientFactory recipientFactory;
	private final FcmClient fcmClient;
	private final MemberNotificationFinder memberNotificationFinder;
	private final FcmTokenFinder fcmTokenFinder;

	public void registerFcmToken(long memberId, FcmTokenSaveRequest fcmTokenSaveRequest) {
		fcmClient.registerToken(memberId, fcmTokenSaveRequest.token());
	}

	public void notifyToMember(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender, long recipientId) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);

		List<String> tokens = fcmTokenFinder.findAllByRecipientId(recipientId);
		fcmClient.sendNotification(notification, tokens);
	}

	public void notifyToMembers(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);
		List<Long> recipients = recipientFactory.getStrategy(type)
			.resolveRecipients(darakbangId, notification, moim, sender);

		List<String> tokens = fcmTokenFinder.findAllByRecipients(recipients);
		fcmClient.sendNotification(notification, tokens);
	}

	public NotificationFindAllResponses findAllMyNotifications(Member member, Long darakbangId) {
		List<NotificationFindAllResponse> responses = memberNotificationFinder.findAll(member, darakbangId)
			.stream()
			.map(MemberNotification::getMoudaNotification)
			.map(NotificationFindAllResponse::from)
			.toList();

		return new NotificationFindAllResponses(responses);
	}
}
