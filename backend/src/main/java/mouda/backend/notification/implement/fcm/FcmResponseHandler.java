package mouda.backend.notification.implement.fcm;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.FcmFailedResponse;
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.implement.fcm.token.FcmTokenFinder;
import mouda.backend.notification.implement.fcm.token.FcmTokenWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmResponseHandler {

	private static final int MAX_ATTEMPT = 3;
	private static final int BACKOFF_DELAY_FOR_SECONDS = 10;
	private static final int BACKOFF_MULTIPLIER = 1;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
	private final FcmMessageFactory fcmMessageFactory;
	private final FcmTokenFinder fcmTokenFinder;
	private final FcmTokenWriter fcmTokenWriter;

	@PreDestroy
	public void destroy() {
		scheduler.shutdown();
	}

	public void handleBatchResponse(
		BatchResponse batchResponse, CommonNotification notification, List<String> initialTokens
	) {
		List<FcmToken> tokens = fcmTokenFinder.readAllByTokensIn(initialTokens);
		FcmFailedResponse failedResponse = FcmFailedResponse.from(batchResponse, tokens);

		int attempt = 1;
		retryAsync(notification, failedResponse, attempt, BACKOFF_DELAY_FOR_SECONDS);
	}

	private void retryAsync(
		CommonNotification notification, FcmFailedResponse failedResponse, int attempt, int backoffDelayForSeconds
	) {
		if (attempt > MAX_ATTEMPT) {
			log.info("Max attempt reached for title: {}, body: {}, failed: {}", notification.getTitle(),
				notification.getBody(), failedResponse.getFinallyFailedTokens());
			removeAllUnregisteredTokens(failedResponse.getFailedWith404Tokens());
			return;
		}
		if (failedResponse.hasNoRetryableTokens()) {
			log.info("No Retryable tokens for title: {}, body: {}, failed: {}.", notification.getTitle(),
				notification.getBody(), failedResponse.getNonRetryableFailedTokens());
			removeAllUnregisteredTokens(failedResponse.getFailedWith404Tokens());
			return;
		}
		retryUsingRetryAfter(notification, failedResponse, attempt, backoffDelayForSeconds);
		retryUsingBackoff(notification, failedResponse, attempt, backoffDelayForSeconds);
	}

	private void removeAllUnregisteredTokens(List<FcmToken> failedWith404Tokens) {
		if (failedWith404Tokens.isEmpty()) {
			return;
		}
		log.info("Removing all unregistered tokens: {}", failedWith404Tokens);
		List<String> tokens = failedWith404Tokens.stream().map(FcmToken::getToken).toList();

		fcmTokenWriter.deleteAll(tokens);
	}

	private void retryUsingRetryAfter(
		CommonNotification notification, FcmFailedResponse failedResponse, int attempt, int backOffDelayForSeconds
	) {
		List<FcmToken> failedWith429Tokens = failedResponse.getFailedWith429Tokens();
		if (failedWith429Tokens.isEmpty()) {
			return;
		}

		int retryAfterSeconds = failedResponse.getRetryAfterSeconds();
		scheduler.schedule(() -> {
			log.info("Retrying 429 retry for title: {}, body: {}, tokens: {}.", notification.getTitle(),
				notification.getBody(), failedWith429Tokens);

			FcmFailedResponse retryResponse = sendNotification(failedResponse, notification, failedWith429Tokens);
			retryAsync(notification, retryResponse, attempt + 1, backOffDelayForSeconds * BACKOFF_MULTIPLIER);
		}, retryAfterSeconds, TimeUnit.SECONDS);
	}

	private void retryUsingBackoff(
		CommonNotification notification, FcmFailedResponse failedResponse, int attempt, int backoffDelayForSeconds
	) {
		List<FcmToken> failedWith5xxTokens = failedResponse.getFailedWith5xxTokens();
		if (failedWith5xxTokens.isEmpty()) {
			return;
		}

		scheduler.schedule(() -> {
			log.info("Retrying 5xx for title: {}, body: {}, tokens: {}.", notification.getTitle(),
				notification.getBody(), failedWith5xxTokens);
			FcmFailedResponse retryResponse = sendNotification(failedResponse, notification, failedWith5xxTokens);
			retryAsync(notification, retryResponse, attempt + 1, backoffDelayForSeconds * BACKOFF_MULTIPLIER);
		}, backoffDelayForSeconds, TimeUnit.SECONDS);
	}

	private FcmFailedResponse sendNotification(
		FcmFailedResponse origin, CommonNotification notification, List<FcmToken> retryTokens
	) {
		List<String> tokens = retryTokens.stream().map(FcmToken::getToken).toList();
		MulticastMessage message = fcmMessageFactory.createMessage(notification, tokens).get(0);

		try {
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
			return FcmFailedResponse.from(response, retryTokens);
		} catch (FirebaseMessagingException e) {
			log.error("Error Sending Message while retrying.. title: {}, body: {}, error message: {}",
				notification.getTitle(), notification.getBody(), e.getMessage());
			return origin;
		}
	}
}
