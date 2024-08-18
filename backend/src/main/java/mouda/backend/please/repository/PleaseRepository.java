package mouda.backend.please.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.please.domain.Please;

public interface PleaseRepository extends JpaRepository<Please, Long> {

	List<Please> findAllByDarakbangId(Long darakbangId);

	boolean existsByIdAndDarakbangId(Long pleaseId, Long darakbangId);
}
