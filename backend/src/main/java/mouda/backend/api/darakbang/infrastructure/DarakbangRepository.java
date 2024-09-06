package mouda.backend.api.darakbang.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mouda.backend.core.domain.darakbang.Darakbang;

@Repository
public interface DarakbangRepository extends JpaRepository<Darakbang, Long> {

	boolean existsByName(String name);

	boolean existsByCode(String code);

	Optional<Darakbang> findByCode(String code);
}
