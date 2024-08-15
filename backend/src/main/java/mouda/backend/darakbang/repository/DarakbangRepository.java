package mouda.backend.darakbang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mouda.backend.darakbang.domain.Darakbang;

@Repository
public interface DarakbangRepository extends JpaRepository<Darakbang, Long> {

	boolean existsByName(String name);

	boolean existsByCode(String code);
}
