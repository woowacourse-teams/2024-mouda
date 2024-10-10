package mouda.backend.notification.implement.fcm.token;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mouda.backend.member.domain.Member;
import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import mouda.backend.notification.infrastructure.repository.FcmTokenRepository;

@Component
@RequiredArgsConstructor
public class FcmTokenWriter {

	private final FcmTokenRepository fcmTokenRepository;

	public void saveOrRefresh(Member member, String token) {
		Optional<FcmTokenEntity> tokenEntity = fcmTokenRepository.findByToken(token);
		tokenEntity.ifPresentOrElse(this::refresh, () -> save(member, token));
	}

	private void refresh(FcmTokenEntity tokenEntity) {
		if (tokenEntity.isInactive()) {
			tokenEntity.activate();
		}

		tokenEntity.refresh();
		fcmTokenRepository.save(tokenEntity);
	}

	private void save(Member member, String token) {
		FcmTokenEntity tokenEntity = FcmTokenEntity.builder()
			.memberId(member.getId())
			.token(token)
			.build();
		fcmTokenRepository.save(tokenEntity);
	}

	@Transactional
	public void deleteAll(List<String> unregisteredTokens) {
		fcmTokenRepository.deleteAllByTokenIn(unregisteredTokens);
	}
}
