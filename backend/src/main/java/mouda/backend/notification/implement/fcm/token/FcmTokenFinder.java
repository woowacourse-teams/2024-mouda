package mouda.backend.notification.implement.fcm.token;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.FcmToken;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;

@Component
@RequiredArgsConstructor
public class FcmTokenFinder {

	private final FcmTokenRepository fcmTokenRepository;

	public List<String> findAllTokensByMember(List<Recipient> recipients) {
		List<String> fcmTokens = new ArrayList<>();
		for (Recipient recipient : recipients) {
			List<String> tokens = fcmTokenRepository.findAllByMemberId(recipient.getMemberId())
				.stream()
				.map(FcmTokenEntity::getToken).toList();
			fcmTokens.addAll(tokens);
		}
		return fcmTokens;
	}

	public List<FcmToken> readAllByTokensIn(List<String> tokens) {
		return fcmTokenRepository.findAllByTokenIn(tokens).stream()
			.map(entity -> FcmToken.builder()
				.memberId(entity.getMemberId())
				.token(entity.getToken())
				.build()
			)
			.toList();
	}
}
