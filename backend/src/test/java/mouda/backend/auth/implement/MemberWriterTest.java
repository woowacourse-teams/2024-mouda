package mouda.backend.auth.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.MemberFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.implement.MemberWriter;
import mouda.backend.member.infrastructure.MemberRepository;

@SpringBootTest
class MemberWriterTest {

	@Autowired
	MemberWriter memberWriter;

	@Autowired
	MemberRepository memberRepository;

	@DisplayName("새로운 멤버를 추가한다.")
	@Test
	void append() {
		// given
		Member tebah = MemberFixture.getTebah();

		// when
		memberWriter.append(tebah);

		// then
		Member savedMember = memberRepository.save(tebah);
		assertThat(savedMember.getId()).isEqualTo(1L);
		assertThat(memberRepository.findAll()).hasSize(1);
	}
}
