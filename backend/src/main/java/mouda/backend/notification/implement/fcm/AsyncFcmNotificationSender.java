package mouda.backend.notification.implement.fcm;

import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
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

@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncFcmNotificationSender {

	private static final int THREAD_POOL_SIZE_FOR_CALLBACK = 5;

	private final FcmResponseHandler fcmResponseHandler;

	@Async
	public void sendAllMulticastMessage(
		CommonNotification notification, List<MulticastMessage> messages, List<FcmToken> tokens
	) {
		if (tokens.isEmpty()) {
			return;
		}

		messages.forEach(multicastMessage -> sendSingleMulticastMessage(notification, multicastMessage, tokens));
	}

	private void sendSingleMulticastMessage(
		CommonNotification notification, MulticastMessage message, List<FcmToken> initialTokens
	) {
		ApiFuture<BatchResponse> future = FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);
		ApiFutures.addCallback(future, new ApiFutureCallback<>() {
			@Override
			public void onFailure(Throwable t) {
				if (t instanceof FirebaseMessagingException exception) {
					log.error(
						"Error Sending Message. title: {}, body: {}, error code: {}, messaging error code: {}, error message: {}",
						notification.getTitle(), notification.getBody(), exception.getErrorCode(),
						exception.getMessagingErrorCode(), exception.getMessage()
					);
				}
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
