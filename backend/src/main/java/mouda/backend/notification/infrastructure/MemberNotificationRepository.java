package mouda.backend.notification.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.notification.domain.MemberNotification;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {

	List<MemberNotification> findAllByMemberIdAndDarakbangIdOrderByIdDesc(Long memberId, Long darakbangId);
}
