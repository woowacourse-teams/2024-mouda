package mouda.backend.darakbang.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mouda.backend.darakbang.domain.Darakbang;

@Repository
public interface DarakbangRepository extends JpaRepository<Darakbang, Long> {

	boolean existsByName(String name);

	boolean existsByCode(String code);

	Optional<Darakbang> findByCode(String code);

	@Query("""
		SELECT d
		FROM Darakbang d
		WHERE d.id IN :darakbangIds
		""")
	List<Darakbang> findAllByIds(List<Long> darakbangIds);
}
