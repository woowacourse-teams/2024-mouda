package mouda.backend.moim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	List<Moim> findAllByDarakbangIdOrderByIdDesc(Long darakbangId);
}
