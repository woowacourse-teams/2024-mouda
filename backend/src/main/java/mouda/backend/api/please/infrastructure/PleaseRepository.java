package mouda.backend.api.please.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.core.domain.please.Please;

public interface PleaseRepository extends JpaRepository<Please, Long> {

	List<Please> findAllByDarakbangIdOrderByIdDesc(Long darakbangId);
}
