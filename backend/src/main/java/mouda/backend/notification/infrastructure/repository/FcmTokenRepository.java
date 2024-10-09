package mouda.backend.notification.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {

    FcmTokenEntity findByMemberId(long memberId);

    List<FcmTokenEntity> findAllByMemberId(long memberId);

    Optional<FcmTokenEntity> findByToken(String token);

	void deleteAllByTokenIn(List<String> unregisteredTokens);
}
