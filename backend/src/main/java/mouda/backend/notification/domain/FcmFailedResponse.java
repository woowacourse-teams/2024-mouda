package mouda.backend.notification.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.SendResponse;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.notification.util.FcmRetryAfterExtractor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FcmFailedResponse {

	private final BatchResponse batchResponse;
	private final Map<MessagingErrorCode, List<FcmToken>> failedTokens;

	public static FcmFailedResponse from(BatchResponse response, List<FcmToken> triedTokens) {
		Map<MessagingErrorCode, List<FcmToken>> result = new ConcurrentHashMap<>();
		List<SendResponse> responses = response.getResponses();
		IntStream.range(0, responses.size())
			.forEach(i -> {
				SendResponse sendResponse = responses.get(i);
				if (sendResponse.isSuccessful()) {
					return;
				}
				FcmToken token = triedTokens.get(i);
				MessagingErrorCode errorCode = sendResponse.getException().getMessagingErrorCode();
				result.computeIfAbsent(errorCode, k -> new ArrayList<>()).add(token);
			});

		return new FcmFailedResponse(response, result);
	}

	public List<FcmToken> getFailedWith404Tokens() {
		return getTokens(this::isFailedWith404);
	}

	public List<FcmToken> getFailedWith429Tokens() {
		return getTokens(this::isFailedWith429);
	}

	public List<FcmToken> getFailedWith5xxTokens() {
		return getTokens(this::isFailedWith5xx);
	}

	public List<FcmToken> getNonRetryableFailedTokens() {
		return getTokens(errorCode -> !isFailedWith429(errorCode) && !isFailedWith5xx(errorCode));
	}

	public List<FcmToken> getFinallyFailedTokens() {
		return failedTokens.values().stream()
			.flatMap(List::stream)
			.toList();
	}

	public int getRetryAfterSeconds() {
		return FcmRetryAfterExtractor.getRetryAfterSeconds(batchResponse);
	}

	public boolean hasNoRetryableTokens() {
		return isTokenAbsent(MessagingErrorCode.QUOTA_EXCEEDED, MessagingErrorCode.INTERNAL,
			MessagingErrorCode.UNAVAILABLE);
	}

	private List<FcmToken> getTokens(Predicate<MessagingErrorCode> filter) {
		return failedTokens.keySet().stream()
			.filter(filter)
			.flatMap(errorCode -> failedTokens.get(errorCode).stream())
			.toList();
	}

	private boolean isFailedWith404(MessagingErrorCode errorCode) {
		return errorCode == MessagingErrorCode.UNREGISTERED;
	}

	private boolean isFailedWith429(MessagingErrorCode errorCode) {
		return errorCode == MessagingErrorCode.QUOTA_EXCEEDED;
	}

	private boolean isFailedWith5xx(MessagingErrorCode errorCode) {
		return errorCode == MessagingErrorCode.INTERNAL ||
			errorCode == MessagingErrorCode.UNAVAILABLE;
	}

	private boolean isTokenAbsent(MessagingErrorCode... errorCodes) {
		return Arrays.stream(errorCodes)
			.map(failedTokens::get)
			.allMatch(tokens -> tokens == null || tokens.isEmpty());
	}
}
