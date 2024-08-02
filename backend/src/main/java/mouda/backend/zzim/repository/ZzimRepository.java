package mouda.backend.zzim.repository;

import java.util.List;
import java.util.Optional;
import mouda.backend.zzim.domain.Zzim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZzimRepository extends JpaRepository<Zzim, Long> {

    boolean existsByMoimIdAndMemberId(Long moimId, Long memberId);

    Optional<Zzim> findByMoimIdAndMemberId(Long moimId, Long memberId);

    void deleteAllByMoimId(Long moimId);

    List<Zzim> findAllByMemberId(Long memberId);
}
