package mouda.backend.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findAllByMoimId(long moimId);
}
