package mouda.backend.bet.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.bet.entity.BetEntity;

public interface BetRepository extends JpaRepository<BetEntity, Long> {
	
	List<BetEntity> findAllByDarakbangId(long darakbangId);

	Optional<BetEntity> findByIdAndDarakbangId(long darakbangId, long betEntityId);

	List<BetEntity> findAllByBettingTimeAndLoserDarakbangMemberIdIsNull(LocalDateTime localDateTime);
}
