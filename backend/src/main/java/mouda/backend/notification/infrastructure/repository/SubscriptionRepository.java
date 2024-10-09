package mouda.backend.notification.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	Optional<SubscriptionEntity> findByMemberId(long memberId);
}
