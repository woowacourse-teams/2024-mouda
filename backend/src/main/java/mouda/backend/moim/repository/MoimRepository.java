package mouda.backend.moim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimStatus;

public interface MoimRepository extends JpaRepository<Moim, Long> {

	@Query("""
			UPDATE Moim m
			SET m.moimStatus = :status
			WHERE m.id = :id
		""")
	@Modifying
	int updateMoimStatusById(@Param("id") Long moimId, @Param("status") MoimStatus status);

	@Query("""
			SELECT m From Moim m
			WHERE m.darakbangId = :darakbangId AND m.moimStatus = 'MOIMING'
			ORDER BY m.id DESC
		""")
	List<Moim> findAllByDarakbangIdOrderByIdDesc(@Param("darakbangId") Long darakbangId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select m from Moim m where m.id = :moimId")
	Optional<Moim> findByIdForUpdate(@Param("moimId") Long moimId);
}
