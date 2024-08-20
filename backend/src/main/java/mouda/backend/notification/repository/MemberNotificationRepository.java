package mouda.backend.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.domain.MemberNotification;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {

	List<MemberNotification> findAllByMemberIdAndDarakbangId(Long memberId, Long darakbangId);
}
