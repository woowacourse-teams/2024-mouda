package mouda.backend.chat.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import mouda.backend.chat.domain.Chat;
import mouda.backend.fixture.ChatFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@DataJpaTest
class ChatRepositoryTest {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("모임 아이디가 동일하고 채팅 아이디가 더 큰 채팅 리스트가 조회된다.")
	@Test
	void test() {
		Moim moim = MoimFixture.getCoffeeMoim();
		moimRepository.save(moim);

		Member member1 = MemberFixture.getAnna();
		Member member2 = MemberFixture.getHogee();

		memberRepository.save(member1);
		memberRepository.save(member2);

		Chat hogee = ChatFixture.getChatWithHogeeAtCoffeeMoim();
		chatRepository.save(hogee);
		Chat anna = ChatFixture.getChatWithAnnaAtCoffeeMoim();
		chatRepository.save(anna);

		List<Chat> chats = chatRepository.findAllUnloadedChats(1L, 1L);

		assertThat(chats).hasSize(1);
	}
}
