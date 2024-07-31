package mouda.backend.security;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;

@SpringBootTest
class JwtProviderTest {

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("토큰을 발급한다.")
	@Test
	void createToken() {
		Member member = MemberFixture.getTebah();
		Member savedMember = memberRepository.save(member);
		String token = jwtProvider.createToken(savedMember);

		String savedNickname = jwtProvider.extractNickname(token);

		assertThat(savedNickname).isEqualTo(member.getNickname());
	}
}
