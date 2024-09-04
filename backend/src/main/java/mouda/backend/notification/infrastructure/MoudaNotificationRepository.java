package mouda.backend.notification.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.domain.MoudaNotification;

public interface MoudaNotificationRepository extends JpaRepository<MoudaNotification, Long> {
}
