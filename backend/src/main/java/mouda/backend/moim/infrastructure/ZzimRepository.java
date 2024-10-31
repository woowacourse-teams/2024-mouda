package mouda.backend.moim.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.moim.domain.Zzim;
import org.springframework.data.jpa.repository.Query;

public interface ZzimRepository extends JpaRepository<Zzim, Long> {

	boolean existsByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	Optional<Zzim> findByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	List<Zzim> findAllByDarakbangMemberIdOrderByIdDesc(Long darakbangMemberId);

	@Query("SELECT z.moim.id FROM Zzim z WHERE z.darakbangMember.id = :darakbangMemberId AND z.moim.id IN :moimIds")
	List<Long> findZzimedMoimByMoimIdsAndDarakbangMemberId(List<Long> moimIds, Long darakbangMemberId);
}
