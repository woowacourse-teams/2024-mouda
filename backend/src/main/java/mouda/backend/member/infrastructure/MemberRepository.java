package mouda.backend.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByKakaoId(Long kakaoId);
}
