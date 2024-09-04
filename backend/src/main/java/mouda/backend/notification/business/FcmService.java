package mouda.backend.notification.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.infrastructure.FcmTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

	private final FcmTokenRepository fcmTokenRepository;

	public void sendNotification(MoudaNotification notification, List<String> tokens) {
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

	public void registerToken(long memberId, String token) {
		fcmTokenRepository.findByToken(token)
			.ifPresentOrElse(
				FcmToken::refreshTimestamp,
				() -> {
					FcmToken fcmToken = FcmToken.builder()
						.memberId(memberId)
						.fcmToken(token)
						.build();
					fcmTokenRepository.save(fcmToken);
				}
			);
	}

	@Scheduled(cron = "0 0 0 1 * *")
	public void cleanInactiveTokens() {
		fcmTokenRepository.findAll()
			.forEach(token -> {
				if (token.getTimestamp().isBefore(LocalDateTime.now().minusMonths(1L))) {
					fcmTokenRepository.delete(token);
				}
			});
	}
}
