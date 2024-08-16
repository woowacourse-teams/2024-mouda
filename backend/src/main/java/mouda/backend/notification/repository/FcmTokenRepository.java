package mouda.backend.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.notification.domain.FcmToken;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

	Optional<FcmToken> findFcmTokenByMemberId(long memberId);

	@Query("SELECT f.token FROM FcmToken f WHERE f.memberId IN :memberIds")
	List<String> findTokensByMemberIds(@Param("memberIds") List<Long> memberIds);

	@Query("SELECT f.memberId FROM FcmToken f")
	List<Long> findAllMemberId();
}
