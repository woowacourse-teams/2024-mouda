package mouda.backend.moim.repository;

import mouda.backend.moim.domain.Moim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoimRepository extends JpaRepository<Moim, Long> {
}
