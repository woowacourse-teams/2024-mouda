package mouda.backend.darakbangmember.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mouda.backend.darakbangmember.domain.DarakbangMember;

@Repository
public interface DarakbangMemberRepository extends JpaRepository<DarakbangMember, Long> {

	@Query("""
		SELECT dm.darakbang.id
		FROM DarakbangMember dm
		WHERE dm.memberId = :memberId
		""")
	List<Long> findAllDarakbangIdsByMemberId(long memberId);

	Optional<DarakbangMember> findByDarakbangIdAndMemberId(Long darakbangId, Long id);

	boolean existsByDarakbangIdAndMemberId(Long darakbangId, Long id);

	boolean existsByDarakbangIdAndNickname(Long id, String nickname);

	List<DarakbangMember> findAllByDarakbangId(Long darakbangId);
}
