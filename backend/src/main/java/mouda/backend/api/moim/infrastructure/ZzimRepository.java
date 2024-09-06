package mouda.backend.api.moim.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.core.domain.moim.Zzim;

public interface ZzimRepository extends JpaRepository<Zzim, Long> {

	boolean existsByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	Optional<Zzim> findByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	void deleteAllByMoimId(Long moimId);

	List<Zzim> findAllByDarakbangMemberIdOrderByIdDesc(Long darakbangMemberId);
}
