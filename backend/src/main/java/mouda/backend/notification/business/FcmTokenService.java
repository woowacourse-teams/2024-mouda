package mouda.backend.notification.business;

import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;
import mouda.backend.notification.presentation.request.FcmTokenRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmTokenService {

	private final FcmTokenRepository fcmTokenRepository;

	public void saveOrRefreshToken(Long memberId, FcmTokenRequest tokenRequest) {
		String token = tokenRequest.token();
		Optional<FcmTokenEntity> tokenEntity = fcmTokenRepository.findByToken(token);
		tokenEntity.ifPresentOrElse(this::refresh, () -> save(memberId, token));
	}

	private void refresh(FcmTokenEntity tokenEntity) {
		if (tokenEntity.isInactive()) {
			tokenEntity.activate();
		}

		tokenEntity.refresh();
		fcmTokenRepository.save(tokenEntity);
	}

	private void save(Long memberId, String token) {
		FcmTokenEntity tokenEntity = FcmTokenEntity.builder().memberId(memberId).token(token).build();
		fcmTokenRepository.save(tokenEntity);
	}

	@Scheduled(cron = "0 0 0 1 * ?")
	public void checkAllTokensIfInactiveOrExpired() {
		fcmTokenRepository.findAll().forEach(this::deactiveOrDelete);
	}

	private void deactiveOrDelete(FcmTokenEntity tokenEntity) {
		if (tokenEntity.isExpired()) {
			fcmTokenRepository.delete(tokenEntity);
			return;
		}
		if (tokenEntity.isInactive()) {
			tokenEntity.refresh();
			tokenEntity.deactivate();
			fcmTokenRepository.save(tokenEntity);
		}
	}
}
