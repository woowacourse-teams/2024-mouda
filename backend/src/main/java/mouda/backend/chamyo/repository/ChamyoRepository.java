package mouda.backend.chamyo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.moim.domain.Moim;

public interface ChamyoRepository extends JpaRepository<Chamyo, Long> {

	Optional<Chamyo> findByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	List<Chamyo> findAllByMoimId(Long moimId);

	int countByMoim(Moim moim);

	boolean existsByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	List<Chamyo> findAllByDarakbangMemberId(Long darakbangMemberId);

	void deleteAllByMoimId(Long moimId);

	void deleteByMoimIdAndDarakbangMemberId(Long moimId, Long darakbangMemberId);

	@Query("SELECT c.darakbangMember.memberId FROM Chamyo c WHERE c.moim.id = :moimId AND c.moimRole = 'MOIMER'")
	Long findMoimerIdByMoimId(@Param("moimId") Long moimId);

	List<Chamyo> findAllByDarakbangMemberIdAndMoim_DarakbangId(Long darakbangMemberId, Long darakbangId);
}
