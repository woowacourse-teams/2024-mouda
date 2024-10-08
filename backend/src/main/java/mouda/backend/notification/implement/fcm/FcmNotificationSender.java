package mouda.backend.notification.implement.fcm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.implement.NotificationSender;
import mouda.backend.notification.implement.fcm.token.FcmTokenFinder;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmNotificationSender implements NotificationSender {

	private final FcmMessageFactory fcmMessageFactory;
	private final FcmTokenFinder fcmTokenFinder;
	private final FcmResponseHandler fcmResponseHandler;

	@Override
	public void sendNotification(CommonNotification notification, List<Recipient> recipients) {
		List<String> tokens = fcmTokenFinder.findAllTokensByMember(recipients);
		sendAllMulticastMessage(notification, tokens);
	}

	private void sendAllMulticastMessage(CommonNotification notification, List<String> tokens) {
		if (tokens.isEmpty()) {
			return;
		}

		fcmMessageFactory.createMessage(notification, tokens).forEach(this::sendMulticastMessage);
	}

	private void sendMulticastMessage(MulticastMessage message) {
		try {
			BatchResponse batchResponse = FirebaseMessaging.getInstance().sendEachForMulticast(message);
			fcmResponseHandler.handleBatchResponse(batchResponse);
		} catch (FirebaseMessagingException e) {
			log.error("Error Sending Message. error code: {}, messaging error code: {}, error message: {}",
				e.getErrorCode(), e.getMessagingErrorCode(), e.getMessage());
		}
	}
}
