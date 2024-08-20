package mouda.backend.chamyo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.moim.domain.Moim;

public interface ChamyoRepository extends JpaRepository<Chamyo, Long> {

	Optional<Chamyo> findByMoimIdAndMemberId(Long moimId, Long id);

	List<Chamyo> findAllByMoimId(Long moimId);

	int countByMoim(Moim moim);

	boolean existsByMoimIdAndMemberId(Long moimId, Long memberId);

	List<Chamyo> findAllByMemberId(Long memberId);

	void deleteAllByMoimId(Long moimId);

	void deleteByMoimIdAndMemberId(Long moimId, Long memberId);

	@Query("SELECT c.member.member.id FROM Chamyo c WHERE c.moim.id = :moimId AND c.moimRole = 'MOIMER'")
	Long findMoimerIdByMoimId(@Param("moimId") Long moimId);

	List<Chamyo> findAllByMemberIdAndMoim_DarakbangId(Long memberId, Long darakbangId);
}
