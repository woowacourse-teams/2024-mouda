package mouda.backend.please.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.please.domain.Please;

public interface PleaseRepository extends JpaRepository<Please, Long> {
}
