package mouda.backend.notification.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.infrastructure.entity.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	SubscriptionEntity findByMemberId(long memberId);
}
