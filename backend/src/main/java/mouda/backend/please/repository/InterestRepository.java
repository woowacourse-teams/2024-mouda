package mouda.backend.please.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.please.domain.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {

	boolean existsByMemberId(Long id);

	long countByPleaseId(Long pleaseId);

	boolean existsByMemberIdAndPleaseId(Long memberId, long pleaseId);

	Optional<Interest> findByMemberIdAndPleaseId(Long memberId, long pleasedId);
}
