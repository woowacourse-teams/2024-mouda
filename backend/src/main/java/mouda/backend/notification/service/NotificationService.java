package mouda.backend.notification.service;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;
import mouda.backend.notification.repository.FcmTokenRepository;
import mouda.backend.notification.repository.MemberNotificationRepository;
import mouda.backend.notification.repository.MoudaNotificationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

	private final FcmTokenRepository fcmTokenRepository;
	private final MoudaNotificationRepository moudaNotificationRepository;
	private final MemberNotificationRepository memberNotificationRepository;

	public void registerFcmToken(long memberId, FcmTokenSaveRequest fcmTokenSaveRequest) {
		FcmToken fcmToken = FcmToken.builder()
			.memberId(memberId)
			.fcmToken(fcmTokenSaveRequest.token())
			.build();

		fcmTokenRepository.save(fcmToken);
	}

	public void notifyToMember(MoudaNotification moudaNotification, Member member) {
		MoudaNotification notification = moudaNotificationRepository.save(moudaNotification);
		memberNotificationRepository.save(MemberNotification.builder()
			.memberId(member.getId())
			.moudaNotification(notification)
			.build());

		String fcmToken = fcmTokenRepository.findFcmTokenByMemberId(member.getId())
			.orElseThrow(
				() -> new NotificationException(HttpStatus.NOT_FOUND, NotificationErrorMessage.FCM_TOKEN_NOT_FOUND))
			.getToken();

		sendNotification(notification, fcmToken);
	}

	private void sendNotification(MoudaNotification moudaNotification, String fcmToken) {
		Message message = Message.builder()
			.setToken(fcmToken)
			.setNotification(moudaNotification.toFcmNotification())
			.setWebpushConfig(getWebpushConfig(moudaNotification.getTargetUrl()))
			.build();

		try {
			FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			log.error("Failed to send message: {}", e.getMessage());
		}
	}

	public void notifyToAllMembers(MoudaNotification moudaNotification) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId();

		notifyToMembers(moudaNotification, allMemberId);
	}

	public void notifyToAllMembersExcept(MoudaNotification moudaNotification, Long exceptMemberId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !Objects.equals(memberId, exceptMemberId))
			.toList();

		notifyToMembers(moudaNotification, allMemberId);
	}

	public void notifyToAllMembersExcept(MoudaNotification moudaNotification, List<Long> exceptMemberIds) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !exceptMemberIds.contains(memberId))
			.toList();

		notifyToMembers(moudaNotification, allMemberId);
	}

	public void notifyToMembers(MoudaNotification moudaNotification, List<Long> memberIds) {
		MoudaNotification notification = moudaNotificationRepository.save(moudaNotification);

		memberNotificationRepository.saveAll(memberIds.stream()
			.map(memberId -> MemberNotification.builder()
				.memberId(memberId)
				.moudaNotification(notification)
				.build())
			.toList());

		List<String> tokens = fcmTokenRepository.findTokensByMemberIds(memberIds);

		sendNotificationToAll(notification, tokens);
	}

	private void sendNotificationToAll(MoudaNotification notification, List<String> tokens) {
		MulticastMessage message = MulticastMessage.builder()
			.addAllTokens(tokens)
			.setNotification(notification.toFcmNotification())
			.setWebpushConfig(getWebpushConfig(notification.getTargetUrl()))
			.build();

		try {
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
			log.info("Successfully sent message: {}", response.getSuccessCount());
		} catch (FirebaseMessagingException e) {
			log.error("Failed to send message: {}", e.getMessage());
		}
	}

	private WebpushConfig getWebpushConfig(String url) {
		return WebpushConfig.builder()
			.setFcmOptions(WebpushFcmOptions.withLink(url))
			.build();
	}
}
