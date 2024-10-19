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

	Optional<Member> findByLoginDetail_Identifier(String identifier);

	@Query("""
		UPDATE Member m
		SET m.loginDetail.oauthType = :oauthType, m.loginDetail.identifier = :identifier
		WHERE m.id = :memberId
		""")
	@Modifying
	@Transactional
	void updateLoginDetail(@Param("memberId") long memberId, @Param("oauthType") OauthType oauthType,
		@Param("identifier") String identifier);

	@Query("""
		UPDATE Member m
		SET m.name = :name
		WHERE m.id = :memberId
		""")
	@Modifying
	@Transactional
	void updateName(@Param("memberId") long memberId, @Param("name") String name);
}
