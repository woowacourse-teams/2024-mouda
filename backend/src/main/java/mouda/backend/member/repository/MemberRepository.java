package mouda.backend.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mouda.backend.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("SELECT m.nickName FROM Member m WHERE m.moim.id = :moimId")
	List<String> findNickNamesByMoimId(long moimId);
}
