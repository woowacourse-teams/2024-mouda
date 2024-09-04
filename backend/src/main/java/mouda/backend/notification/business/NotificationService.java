package mouda.backend.notification.business;

import java.util.List;
import java.util.Objects;

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
import mouda.backend.notification.infrastructure.FcmTokenRepository;
import mouda.backend.notification.infrastructure.MemberNotificationRepository;
import mouda.backend.notification.presentation.request.FcmTokenSaveRequest;
import mouda.backend.notification.presentation.response.NotificationFindAllResponse;
import mouda.backend.notification.presentation.response.NotificationFindAllResponses;
import mouda.backend.notification.service.FcmService;
import mouda.backend.notification.service.NotificationFactory;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = FirebaseException.class)
public class NotificationService {

	private final NotificationFactory notificationFactory;
	private final RecipientFactory recipientFactory;
	private final FcmService fcmService;
	private final MemberNotificationRepository memberNotificationRepository;
	private final FcmTokenRepository fcmTokenRepository;

	public void registerFcmToken(long memberId, FcmTokenSaveRequest fcmTokenSaveRequest) {
		fcmService.registerToken(memberId, fcmTokenSaveRequest.token());
	}

	public void notifyToMember(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender, long recipientId) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);

		List<String> tokens = fcmTokenRepository.findAllTokenByMemberId(recipientId);
		fcmService.sendNotification(notification, tokens);
	}

	public void notifyToAllMembers(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);

		List<Long> recipients = fcmTokenRepository.findAllMemberId();
		List<String> tokens = fcmTokenRepository.findAllTokenByMemberIds(recipients);

		fcmService.sendNotification(notification, tokens);
	}

	public void notifyToAllExceptMember(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender, Long exceptMemberId) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);

		List<Long> recipients = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !Objects.equals(memberId, exceptMemberId))
			.toList();
		List<String> tokens = fcmTokenRepository.findAllTokenByMemberIds(recipients);

		fcmService.sendNotification(notification, tokens);
	}

	public void notifyToAllExceptMember(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender, List<Long> exceptMemberIds
	) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);

		List<Long> recipients = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !exceptMemberIds.contains(memberId))
			.toList();
		List<String> tokens = fcmTokenRepository.findAllTokenByMemberIds(recipients);

		fcmService.sendNotification(notification, tokens);
	}

	public void notifyToMembers(NotificationType type, Long darakbangId, Moim moim,
		DarakbangMember sender) {
		MoudaNotification notification = notificationFactory.getStrategy(type)
			.buildNotification(darakbangId, moim, sender);
		List<Long> recipients = recipientFactory.getStrategy(type)
			.resolveRecipients(darakbangId, notification, moim, sender);

		List<String> tokens = fcmTokenRepository.findAllTokenByMemberIds(recipients);
		fcmService.sendNotification(notification, tokens);
	}

	public NotificationFindAllResponses findAllMyNotifications(Member member, Long darakbangId) {
		List<NotificationFindAllResponse> responses = memberNotificationRepository.findAllByMemberIdAndDarakbangIdOrderByIdDesc(
				member.getId(), darakbangId)
			.stream()
			.map(MemberNotification::getMoudaNotification)
			.map(NotificationFindAllResponse::from)
			.toList();

		return new NotificationFindAllResponses(responses);
	}
}
