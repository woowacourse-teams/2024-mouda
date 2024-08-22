package mouda.backend.notification.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.domain.MemberNotification;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.dto.request.FcmTokenSaveRequest;
import mouda.backend.notification.dto.response.NotificationFindAllResponse;
import mouda.backend.notification.dto.response.NotificationFindAllResponses;
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
		fcmTokenRepository.findByToken(fcmTokenSaveRequest.token())
			.ifPresentOrElse(
				FcmToken::refreshTimestamp,
				() -> {
					FcmToken fcmToken = FcmToken.builder()
						.memberId(memberId)
						.fcmToken(fcmTokenSaveRequest.token())
						.build();
					fcmTokenRepository.save(fcmToken);
				}
			);
	}

	@Scheduled(cron = "0 0 0 1 * *")
	public void cleanInactiveFcmTokens() {
		fcmTokenRepository.findAll()
			.forEach(token -> {
				if (token.getTimestamp().isBefore(LocalDateTime.now().minusMonths(1L))) {
					fcmTokenRepository.delete(token);
				}
			});
	}

	public void notifyToMember(MoudaNotification moudaNotification, Long memberId, Long darakbangId) {
		MoudaNotification notification = moudaNotificationRepository.save(moudaNotification);

		if (notification.getType() != NotificationType.NEW_CHAT) {
			memberNotificationRepository.save(MemberNotification.builder()
				.memberId(memberId)
				.darakbangId(darakbangId)
				.moudaNotification(notification)
				.build());
		}

		List<String> tokens = fcmTokenRepository.findAllTokenByMemberId(memberId);
		sendNotificationToAll(notification, tokens);
	}

	public void notifyToAllMembers(MoudaNotification moudaNotification, Long darakbangId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId();

		notifyToMembers(moudaNotification, allMemberId, darakbangId);
	}

	public void notifyToAllExceptMember(MoudaNotification moudaNotification, Long exceptMemberId, Long darakbangId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !Objects.equals(memberId, exceptMemberId))
			.toList();

		notifyToMembers(moudaNotification, allMemberId, darakbangId);
	}

	public void notifyToAllExceptMember(MoudaNotification moudaNotification, List<Long> exceptMemberIds,
		Long darakbangId) {
		List<Long> allMemberId = fcmTokenRepository.findAllMemberId().stream()
			.filter(memberId -> !exceptMemberIds.contains(memberId))
			.toList();

		notifyToMembers(moudaNotification, allMemberId, darakbangId);
	}

	public void notifyToMembers(MoudaNotification moudaNotification, List<Long> memberIds, Long darakbangId) {
		MoudaNotification notification = moudaNotificationRepository.save(moudaNotification);

		if (notification.getType() != NotificationType.NEW_CHAT) {
			memberNotificationRepository.saveAll(memberIds.stream()
				.map(memberId -> MemberNotification.builder()
					.memberId(memberId)
					.darakbangId(darakbangId)
					.moudaNotification(notification)
					.build())
				.toList());
		}

		List<String> tokens = fcmTokenRepository.findAllTokenByMemberIds(memberIds);
		sendNotificationToAll(notification, tokens);
	}

	private void sendNotificationToAll(MoudaNotification notification, List<String> tokens) {
		if (tokens.isEmpty()) {
			return;
		}

		List<List<String>> chunkedTokens = chunkFcmTokensForMulticast(tokens);

		chunkedTokens.stream()
			.map(chunk -> MulticastMessage.builder()
				.setNotification(notification.toFcmNotification())
				.setWebpushConfig(getWebpushConfig(notification.getTargetUrl()))
				.addAllTokens(chunk)
				.build())
			.forEach(message -> {
				try {
					BatchResponse batchResponse = FirebaseMessaging.getInstance().sendEachForMulticast(message);
					validateFcmTokenByErrorCode(tokens, batchResponse);
				} catch (FirebaseMessagingException e) {
					log.error("Failed to send message: {}", e.getMessage());
				}
			});
	}

	private List<List<String>> chunkFcmTokensForMulticast(List<String> tokens) {
		int defaultChunkSize = 500;
		List<List<String>> result = new ArrayList<>();
		for (int i = 0; i < tokens.size(); i += defaultChunkSize) {
			result.add(tokens.subList(i, Math.min(i + defaultChunkSize, tokens.size())));
		}
		return result;
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

	private WebpushConfig getWebpushConfig(String url) {
		return WebpushConfig.builder()
			.setFcmOptions(WebpushFcmOptions.withLink(url))
			.build();
	}

	private void validateFcmTokenByErrorCode(List<String> tokens, BatchResponse batchResponse) {
		if (batchResponse.getFailureCount() == 0) {
			return;
		}

		List<SendResponse> responses = batchResponse.getResponses();
		IntStream.range(0, responses.size())
			.filter(index -> isInvalidTokenErrorCode(responses.get(index)))
			.forEach(index -> fcmTokenRepository.deleteByToken(tokens.get(index)));
	}

	private boolean isInvalidTokenErrorCode(SendResponse sendResponse) {
		if (sendResponse.isSuccessful()) {
			return false;
		}
		MessagingErrorCode errorCode = sendResponse.getException().getMessagingErrorCode();
		return errorCode == MessagingErrorCode.UNREGISTERED || errorCode == MessagingErrorCode.INVALID_ARGUMENT;
	}
}
