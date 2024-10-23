package mouda.backend.notification.util;

import java.util.List;

import com.google.firebase.IncomingHttpResponse;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;

public class FcmRetryAfterExtractor {

	private static final int DEFAULT_RETRY_AFTER_SECONDS = 60;

	public static int getRetryAfterSeconds(BatchResponse batchResponse) {
		List<SendResponse> responses = batchResponse.getResponses();
		return responses.stream()
			.filter(FcmRetryAfterExtractor::isFailedWith429)
			.map(FcmRetryAfterExtractor::parseRetryAfterSeconds)
			.findAny()
			.orElse(DEFAULT_RETRY_AFTER_SECONDS);
	}

	private static boolean isFailedWith429(SendResponse response) {
		return response.getException().getMessagingErrorCode() == MessagingErrorCode.QUOTA_EXCEEDED;
	}

	private static int parseRetryAfterSeconds(SendResponse response) {
		FirebaseMessagingException exception = response.getException();
		IncomingHttpResponse httpResponse = exception.getHttpResponse();
		Object retryAfterHeader = httpResponse.getHeaders().get("Retry-After");

		try {
			return Integer.parseInt(retryAfterHeader.toString());
		} catch (Exception e) {
			return DEFAULT_RETRY_AFTER_SECONDS;
		}
	}
}
