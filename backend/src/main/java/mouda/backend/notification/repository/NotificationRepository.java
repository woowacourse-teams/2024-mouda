package mouda.backend.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
