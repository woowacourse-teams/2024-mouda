package mouda.backend.notification.implement.fcm.token;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;

@Component
@RequiredArgsConstructor
public class FcmTokenScheduler {

	private final FcmTokenRepository fcmTokenRepository;

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
