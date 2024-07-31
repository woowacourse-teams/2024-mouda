package mouda.backend.chamyo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.chamyo.domain.Chamyo;

public interface ChamyoRepository extends JpaRepository<Chamyo, Long> {

	Optional<Chamyo> findByMoimIdAndMemberId(Long moimId, Long id);

	List<Chamyo> findAllByMoimId(Long moimId);
}
