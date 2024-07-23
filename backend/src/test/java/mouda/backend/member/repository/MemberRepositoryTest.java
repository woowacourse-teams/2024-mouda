package mouda.backend.member.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.member.domain.Member;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MoimRepository moimRepository;

	@DisplayName("모임에 가입된 맴버의 이름을 반환한다.")
	@Test
	void findNickNamesByMoimId() {
		Member member = new Member("tehah");
		Moim moim = new Moim().builder()
			.build();
		Moim saveMoim = moimRepository.save(moim);
		member.joinMoim(moim);
		memberRepository.save(member);

		List<String> participants = memberRepository.findNickNamesByMoimId(saveMoim.getId());

		Assertions.assertThat(participants.size()).isEqualTo(1);
	}

}
