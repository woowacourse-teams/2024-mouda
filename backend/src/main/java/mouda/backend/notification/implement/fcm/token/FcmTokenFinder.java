package mouda.backend.notification.implement.fcm.token;

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

	public List<FcmToken> findAllTokensByMemberIn(List<Recipient> recipients) {
		return recipients.stream()
			.flatMap(recipient -> fcmTokenRepository.findAllByMemberId(recipient.getMemberId()).stream())
			.map(this::createByEntity)
			.toList();
	}

	private FcmToken createByEntity(FcmTokenEntity entity) {
		return FcmToken.builder()
			.tokenId(entity.getId())
			.memberId(entity.getMemberId())
			.token(entity.getToken())
			.build();
	}
}
