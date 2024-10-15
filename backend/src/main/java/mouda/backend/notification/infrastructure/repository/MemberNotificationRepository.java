package mouda.backend.notification.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.infrastructure.entity.MemberNotificationEntity;

public interface MemberNotificationRepository extends JpaRepository<MemberNotificationEntity, Long> {

	List<MemberNotificationEntity> findAllByDarakbangMemberId(Long darakbangMemberId);
}
