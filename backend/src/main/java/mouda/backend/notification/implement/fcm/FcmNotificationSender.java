package mouda.backend.notification.implement.fcm;

import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.implement.NotificationSender;
import mouda.backend.notification.implement.fcm.token.FcmTokenFinder;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmNotificationSender implements NotificationSender {

	private static final int MAX_ATTEMPT = 3;
	private static final int THREAD_POOL_SIZE_FOR_CALLBACK = 5;

	private final FcmMessageFactory fcmMessageFactory;
	private final FcmResponseHandler fcmResponseHandler;
	private final FcmTokenFinder fcmTokenFinder;

	@Override
	public void sendNotification(CommonNotification notification, List<Recipient> recipients) {
		List<String> tokens = fcmTokenFinder.findAllTokensByMember(recipients);
		sendAllMulticastMessage(notification, tokens);
	}

	private void sendAllMulticastMessage(CommonNotification notification, List<String> tokens) {
		if (tokens.isEmpty()) {
			return;
		}

		int attempt = 1;
		fcmMessageFactory.createMessage(notification, tokens)
			.forEach(multicastMessage -> sendMulticastMessage(notification, multicastMessage, tokens, attempt));
	}

	private void sendMulticastMessage(
		CommonNotification notification, MulticastMessage message, List<String> initialTokens, int attempt
	) {
		if (attempt > MAX_ATTEMPT) {
			List<FcmToken> tokens = fcmTokenFinder.readAllByTokensIn(initialTokens);
			log.info("Max attempt reached for title: {}, body: {}, failed: {}", notification.getTitle(),
				notification.getBody(), tokens);
			return;
		}

		ApiFuture<BatchResponse> future = FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);
		ApiFutures.addCallback(future, new ApiFutureCallback<>() {
			@Override
			public void onFailure(Throwable t) {
				if (t instanceof FirebaseMessagingException exception) {
					log.error(
						"Error Sending Message. title: {}, body: {}, error code: {}, messaging error code: {}, error message: {}",
						notification.getTitle(), notification.getBody(), exception.getMessagingErrorCode(),
						exception.getMessagingErrorCode(), exception.getMessage()
					);
				}
				sendMulticastMessage(notification, message, initialTokens, attempt + 1);
			}

			@Override
			public void onSuccess(BatchResponse result) {
				if (result.getFailureCount() == 0) {
					log.info("All messages were sent successfully. title: {}, body: {}", notification.getTitle(),
						notification.getBody());
					return;
				}
				fcmResponseHandler.handleBatchResponse(result, notification, initialTokens);
			}
		}, Executors.newFixedThreadPool(THREAD_POOL_SIZE_FOR_CALLBACK));
	}
}
