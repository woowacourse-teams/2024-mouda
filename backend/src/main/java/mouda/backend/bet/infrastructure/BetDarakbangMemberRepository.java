package mouda.backend.bet.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.darakbangmember.domain.DarakbangMember;

public interface BetDarakbangMemberRepository extends JpaRepository<BetDarakbangMemberEntity, Long> {

	@Query("SELECT bdm.darakbangMember FROM BetDarakbangMemberEntity bdm WHERE bdm.bet.id = :betId ")
	List<DarakbangMember> findAllDarakbangMemberByBetId(@Param("betId") Long betId);

	List<BetDarakbangMemberEntity> findAllByBetId(Long id);

	Optional<BetDarakbangMemberEntity> findByBetIdAndDarakbangMemberId(Long betId, Long loserDarakbangMemberId);
}
