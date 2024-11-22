package mouda.backend.moim.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.moim.domain.Zzim;

public interface ZzimRepository extends JpaRepository<Zzim, Long> {

	boolean existsByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	Optional<Zzim> findByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	List<Zzim> findAllByDarakbangMemberIdOrderByIdDesc(Long darakbangMemberId);
}
