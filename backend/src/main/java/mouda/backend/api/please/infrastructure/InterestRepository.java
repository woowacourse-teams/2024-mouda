package mouda.backend.api.please.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.core.domain.please.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {

	boolean existsByDarakbangMemberId(Long id);

	long countByPleaseId(Long pleaseId);

	Optional<Interest> findByDarakbangMemberIdAndPleaseId(long memberId, long pleasedId);

	boolean existsByDarakbangMemberIdAndPleaseId(long memberId, long pleaseId);
}
