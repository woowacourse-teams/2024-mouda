package mouda.backend.member.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import mouda.backend.member.domain.Member;
import mouda.backend.member.domain.OauthType;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByLoginDetail_SocialLoginId(String socialLoginId);

	@Transactional
	@Query("""
		UPDATE Member m 
		SET m.loginDetail.oauthType = :oauthType, m.loginDetail.socialLoginId = :socialLoginId 
		WHERE m.id = :memberId 
		""")
	@Modifying
	void updateLoginDetail(@Param("memberId") long memberId, @Param("oauthType") OauthType oauthType,
		@Param("socialLoginId") String socialLoginId);
}
