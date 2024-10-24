package mouda.backend.notification.implement.fcm;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.domain.CommonNotification;
import mouda.backend.notification.domain.FcmFailedResponse;
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.implement.fcm.token.FcmTokenWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class FcmRetryableChecker {

	private static final int MAX_ATTEMPT = 3;

	private final FcmTokenWriter fcmTokenWriter;

	@Transactional
	public boolean check(CommonNotification notification, FcmFailedResponse failedResponse, int attempt) {
		if (failedResponse.hasNoFailedTokens()) {
			log.info("No failed tokens for title: {}, body: {}.", notification.getTitle(), notification.getBody());
			return false;
		}
		return checkWhenFailedTokensExist(notification, failedResponse, attempt);
	}

	private boolean checkWhenFailedTokensExist(
		CommonNotification notification, FcmFailedResponse failedResponse, int attempt
	) {
		removeAllUnregisteredTokens(failedResponse);
		if (attempt > MAX_ATTEMPT) {
			log.info("Max attempt reached for title: {}, body: {}, tokens: {}", notification.getTitle(),
				notification.getBody(), failedResponse.getFailedTokens());
			return false;
		}
		if (failedResponse.hasNoRetryableTokens()) {
			log.info("No retryable tokens for title: {}, body: {}, tokens: {}", notification.getTitle(), notification.getBody(),
				failedResponse.getNonRetryableFailedTokens());
			return false;
		}
		return true;
	}

	private void removeAllUnregisteredTokens(FcmFailedResponse failedResponse) {
		List<FcmToken> failedWith404Tokens = failedResponse.getFailedWith404Tokens();
		if (failedWith404Tokens.isEmpty()) {
			return;
		}

		log.info("Removing all unregistered tokens: {}", failedWith404Tokens);
		List<String> tokens = failedWith404Tokens.stream().map(FcmToken::getToken).toList();

		fcmTokenWriter.deleteAll(tokens);
		failedResponse.removeFailedWith404Tokens();
	}
}
