package mouda.backend.bet.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.bet.entity.BetEntity;

public interface BetRepository extends JpaRepository<BetEntity, Long> {

	List<BetEntity> findAllByBettingTime(LocalDateTime currentTime);
}
