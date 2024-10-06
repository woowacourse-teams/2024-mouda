package mouda.backend.notification.infrastructure.repository;

import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberNotificationRepository extends JpaRepository<MemberNotificationEntity, Long> {
}
