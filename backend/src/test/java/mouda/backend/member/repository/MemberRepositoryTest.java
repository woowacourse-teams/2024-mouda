package mouda.backend.member.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.config.DatabaseCleaner;
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
		Member member = new Member("tehah");
		Moim moim = Moim.builder()
			.title("모임 제목")
			.date(LocalDate.now().plusDays(1))
			.time(LocalTime.now().minusHours(1))
			.place("서울시 강북구 중앙로 2길 25")
			.maxPeople(10)
			.authorNickname("안나")
			.description("모임 설명입니다.")
			.build();
		Moim saveMoim = moimRepository.save(moim);
		member.joinMoim(saveMoim);
		memberRepository.save(member);

		List<Member> participants = memberRepository.findAllByMoimId(saveMoim.getId());

		Assertions.assertThat(participants.size()).isEqualTo(1);
	}
}
