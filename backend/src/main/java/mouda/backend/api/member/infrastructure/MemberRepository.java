package mouda.backend.api.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mouda.backend.core.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByKakaoId(Long kakaoId);
}
