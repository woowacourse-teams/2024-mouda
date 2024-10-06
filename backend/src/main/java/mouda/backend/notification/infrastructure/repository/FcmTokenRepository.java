package mouda.backend.notification.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import mouda.backend.notification.infrastructure.entity.FcmTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.api.services.storage.model.Channel;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, Long> {

    FcmTokenEntity findByMemberId(long memberId);

    List<FcmTokenEntity> findAllByMemberId(long memberId);

    Optional<FcmTokenEntity> findByToken(String token);

}
