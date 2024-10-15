package mouda.backend.notification.implement.fcm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.implement.NotificationSender;
import mouda.backend.notification.implement.fcm.token.FcmTokenFinder;
import mouda.backend.notification.implement.fcm.token.FcmTokenWriter;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmNotificationSender implements NotificationSender {

	private static final int THREAD_POOL_SIZE_FOR_CALLBACK = 5;

	private final FcmMessageFactory fcmMessageFactory;
	private final FcmTokenFinder fcmTokenFinder;
	private final FcmResponseHandler fcmResponseHandler;
	private final FcmTokenWriter fcmTokenWriter;

	@Override
	public void sendNotification(CommonNotification notification, List<Recipient> recipients) {
		List<String> tokens = fcmTokenFinder.findAllTokensByMember(recipients);
		sendAllMulticastMessage(notification, tokens);
	}

	private void sendAllMulticastMessage(CommonNotification notification, List<String> tokens) {
		if (tokens.isEmpty()) {
			return;
		}

		fcmMessageFactory.createMessage(notification, tokens)
			.forEach(multicastMessage -> sendMulticastMessage(notification, multicastMessage, tokens));
	}

	private void sendMulticastMessage(CommonNotification notification, MulticastMessage message,
		List<String> initialTokens) {
		ApiFuture<BatchResponse> future = FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);
		ApiFutures.addCallback(future, new ApiFutureCallback<>() {
			@Override
			public void onFailure(Throwable t) {
				if (t instanceof FirebaseMessagingException exception) {
					log.error("Error Sending Message. error code: {}, messaging error code: {}, error message: {}",
						exception.getErrorCode(), exception.getMessagingErrorCode(), exception.getMessage());
				}
			}

			@Override
			public void onSuccess(BatchResponse result) {
				if (result.getFailureCount() == 0) {
					log.info("All messages were sent successfully. message: {}", notification.getTitle());
					return;
				}
				List<String> registeredTokens = checkUnregisteredTokensAndDelete(result, initialTokens);
				if (registeredTokens.isEmpty()) {
					return;
				}
				fcmResponseHandler.handleBatchResponse(result, notification, registeredTokens);
			}
		}, Executors.newFixedThreadPool(THREAD_POOL_SIZE_FOR_CALLBACK));
	}

	/**
	 * @param batchResponse 처음 알림을 전송했을 때의 응답
	 * @param tokens        처음 알림을 전송했을 때 사용한 토큰
	 * @return 등록되지 않은(FCM 에서 Unregistered 를 응답한) 토큰을 제거한 뒤, 나머지의 토큰을 반환
	 */
	private List<String> checkUnregisteredTokensAndDelete(BatchResponse batchResponse, List<String> tokens) {
		tokens = new ArrayList<>(tokens);
		List<SendResponse> responses = batchResponse.getResponses();
		List<String> unregisteredTokens = IntStream.range(0, responses.size())
			.filter(i -> isUnregistered(responses.get(i)))
			.mapToObj(tokens::get)
			.toList();

		if (!unregisteredTokens.isEmpty()) {
			log.info("{} of {} tokens are unregistered. Deleting them..", unregisteredTokens.size(), tokens.size());
			fcmTokenWriter.deleteAll(unregisteredTokens);
			tokens.removeAll(unregisteredTokens);
		}

		return tokens;
	}

	private boolean isUnregistered(SendResponse response) {
		if (response.isSuccessful()) {
			return false;
		}
		MessagingErrorCode messagingErrorCode = response.getException().getMessagingErrorCode();
		return messagingErrorCode == MessagingErrorCode.UNREGISTERED;
	}
}
