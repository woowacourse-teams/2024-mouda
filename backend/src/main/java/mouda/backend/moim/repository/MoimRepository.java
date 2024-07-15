package mouda.backend.moim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.moim.domain.Moim;

public interface MoimRepository extends JpaRepository<Moim, Long> {
}
