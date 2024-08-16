package mouda.backend.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findAllByMoimId(long moimId);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findByKakaoId(Long kakaoId);
}
