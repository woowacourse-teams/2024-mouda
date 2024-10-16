package mouda.backend.notification.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class FcmFailedResponse {

	private static final int DEFAULT_RETRY_AFTER_SECONDS = 60;

	private final BatchResponse batchResponse;
	private final List<String> failedWith429Tokens;
	private final List<String> failedWith5xxTokens;
	private final List<String> nonRetryableFailedTokens;


	public static FcmFailedResponse from(BatchResponse response, List<String> triedTokens) {
		List<SendResponse> responses = response.getResponses();
		List<String> failedWith429Tokens = new ArrayList<>();
		List<String> failedWith5xxTokens = new ArrayList<>();
		List<String> nonRetryableFailedTokens = new ArrayList<>();

		IntStream.range(0, responses.size())
			.forEach(i -> {
				SendResponse sendResponse = responses.get(i);
				if (sendResponse.isSuccessful()) {
					return;
				}
				String token = triedTokens.get(i);
				if (isFailedWith429(sendResponse)) {
					failedWith429Tokens.add(token);
					return;
				}
				if (isFailedWith5xx(sendResponse)) {
					failedWith5xxTokens.add(token);
					return;
				}
				nonRetryableFailedTokens.add(token);
			});

		return new FcmFailedResponse(response, failedWith429Tokens, failedWith5xxTokens, nonRetryableFailedTokens);
	}

	private static boolean isFailedWith429(SendResponse response) {
		return hasSameErrorCode(response, MessagingErrorCode.QUOTA_EXCEEDED);
	}

	private static boolean isFailedWith5xx(SendResponse response) {
		return hasSameErrorCode(response, MessagingErrorCode.INTERNAL, MessagingErrorCode.UNAVAILABLE);
	}

	private static boolean hasSameErrorCode(SendResponse response, MessagingErrorCode... errorCodes) {
		if (response.isSuccessful()) {
			return false;
		}
		FirebaseMessagingException exception = response.getException();
		return Arrays.stream(errorCodes)
			.anyMatch(errorCode -> exception.getMessagingErrorCode() == errorCode);
	}

	public boolean hasNoRetryableTokens() {
		return failedWith429Tokens.isEmpty() && failedWith5xxTokens.isEmpty();
	}

	public boolean hasFailedWith429Tokens() {
		return !failedWith429Tokens.isEmpty();
	}

	public boolean hasFailedWith5xxTokens() {
		return !failedWith5xxTokens.isEmpty();
	}

	public List<String> getFinallyFailedTokens() {
		List<String> failedTokens = new ArrayList<>();
		failedTokens.addAll(failedWith429Tokens);
		failedTokens.addAll(failedWith5xxTokens);
		failedTokens.addAll(nonRetryableFailedTokens);
		return failedTokens;
	}

	public int getRetryAfterSeconds() {
		List<SendResponse> responses = batchResponse.getResponses();
		return responses.stream()
			.filter(FcmFailedResponse::isFailedWith429)
			.map(this::parseRetryAfterSeconds)
			.findAny()
			.orElse(DEFAULT_RETRY_AFTER_SECONDS);
	}

	private int parseRetryAfterSeconds(SendResponse response) {
		Object retryAfterHeader = response.getException().getHttpResponse().getHeaders().get("Retry-After");
		if (retryAfterHeader == null) {
			return DEFAULT_RETRY_AFTER_SECONDS;
		}
		try {
			return Integer.parseInt(retryAfterHeader.toString());
		} catch (NumberFormatException e) {
			return DEFAULT_RETRY_AFTER_SECONDS;
		}
	}
}
