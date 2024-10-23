package mouda.backend.member.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.MemberFixture;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void tesT() {
		memberRepository.save(MemberFixture.getAnna());
		memberRepository.findActiveOrDeletedByIdentifier("identifier");
	}
}
