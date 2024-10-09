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

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmResponseHandler {

	private static final int MAX_ATTEMPT = 3;
	private static final int BACKOFF_DELAY_FOR_SECONDS = 10;
	private static final int BACKOFF_MULTIPLIER = 1;

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
	private final FcmMessageFactory fcmMessageFactory;

	@PreDestroy
	public void destroy() {
		scheduler.shutdown();
	}

	public void handleBatchResponse(BatchResponse batchResponse, CommonNotification notification,
		List<String> initialTokens) {
		FcmFailedResponse failedResponse = FcmFailedResponse.from(batchResponse, initialTokens);

		int attempt = 1;
		retryAsync(notification, failedResponse, attempt, BACKOFF_DELAY_FOR_SECONDS);
	}

	private void retryAsync(CommonNotification notification, FcmFailedResponse failedResponse, int attempt,
		int backoffDelayForSeconds) {
		if (attempt > MAX_ATTEMPT) {
			log.info("Max attempt reached for notification: {}. failed: {}", notification.getBody(),
				failedResponse.getFinallyFailedTokens());
			return;
		}

		if (failedResponse.hasNoRetryableTokens()) {
			log.info("No Retryable tokens for notification: {}. failed: {}.", notification.getBody(),
				failedResponse.getNonRetryableFailedTokens());
			return;
		}

		if (failedResponse.hasFailedWith429Tokens()) {
			int retryAfterSeconds = failedResponse.getRetryAfterSeconds();
			scheduler.schedule(() -> {
				log.info("Retrying 429 for notification: {}. Thread: {}", notification.getTitle(),
					Thread.currentThread().getName());
				FcmFailedResponse retryResponse = retry(failedResponse, notification,
					failedResponse.getFailedWith429Tokens());
				retryAsync(notification, retryResponse, attempt + 1, backoffDelayForSeconds * BACKOFF_MULTIPLIER);
			}, retryAfterSeconds, TimeUnit.SECONDS);
		}

		if (failedResponse.hasFailedWith5xxTokens()) {
			scheduler.schedule(() -> {
				log.info("Retrying 5xx for notification: {}. Thread: {}", notification.getTitle(),
					Thread.currentThread().getName());
				FcmFailedResponse retryResponse = retry(failedResponse, notification,
					failedResponse.getNonRetryableFailedTokens());
				retryAsync(notification, retryResponse, attempt + 1, backoffDelayForSeconds * BACKOFF_MULTIPLIER);
			}, backoffDelayForSeconds, TimeUnit.SECONDS);
		}
	}

	private FcmFailedResponse retry(FcmFailedResponse origin, CommonNotification notification,
		List<String> retryTokens) {
		log.info("Retrying for notification: {}. failed: {}, Thread: {}", notification.getTitle(), retryTokens,
			Thread.currentThread().getName());
		MulticastMessage message = fcmMessageFactory.createMessage(notification, retryTokens).get(0);

		try {
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
			return FcmFailedResponse.from(response, retryTokens);
		} catch (FirebaseMessagingException e) {
			log.error("Error Sending Message. error message: {}", e.getMessage());
			return origin;
		}
	}
}
