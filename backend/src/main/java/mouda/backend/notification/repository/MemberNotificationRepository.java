package mouda.backend.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.domain.MemberNotification;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {
}
