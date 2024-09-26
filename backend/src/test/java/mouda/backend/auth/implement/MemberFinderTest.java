package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.auth.exception.AuthException;
import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberFinder;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class MemberFinderTest {

	@Autowired
	MemberFinder memberFinder;

	@Autowired
	MemberRepository memberRepository;

	@DisplayName("멤버 id로 멤버를 찾는다")
	@Test
	void findByMemberId() {
		// given
		Member tebah = MemberFixture.getTebah();
		memberRepository.save(tebah);

		// when
		Member member = memberFinder.find(1L);

		// then
		assertThat(member.getSocialLoginId()).isEqualTo(tebah.getSocialLoginId());
	}

	@DisplayName("멤버가 존재하지 않으면 예외가 발생한다.")
	@Test
	void findByInvalidMemberId() {
		// given
		Member tebah = MemberFixture.getTebah();
		memberRepository.save(tebah);

		// when & than
		assertThatThrownBy(() -> memberFinder.find(2L))
			.isInstanceOf(AuthException.class);
	}
}
