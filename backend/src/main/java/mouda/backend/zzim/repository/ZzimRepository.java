package mouda.backend.zzim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.zzim.domain.Zzim;

public interface ZzimRepository extends JpaRepository<Zzim, Long> {

	boolean existsByMoimIdAndMemberId(Long moimId, Long memberId);
}
