package mouda.backend.member.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
	}

	@DisplayName("모임에 가입된 참여자의 수를 반환한다.")
	@Test
	void findNickNamesByMoimId() {
		Member member = MemberFixture.getHogee();
		Moim moim = MoimFixture.getBasketballMoim();
		Moim saveMoim = moimRepository.save(moim);
		member.joinMoim(saveMoim);
		memberRepository.save(member);

		List<Member> participants = memberRepository.findAllByMoimId(saveMoim.getId());

		Assertions.assertThat(participants.size()).isEqualTo(1);
	}
}
