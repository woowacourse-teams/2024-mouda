package mouda.backend.chat.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chat.domain.Chat;
import mouda.backend.fixture.ChatFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
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

		Chat anna = ChatFixture.getChatWithMemberAtMoim(member1, moim);
		chatRepository.save(anna);
		Chat hogee = ChatFixture.getChatWithMemberAtMoim(member2, moim);
		chatRepository.save(hogee);

		List<Chat> chats = chatRepository.findAllUnloadedChats(1L, 1L);

		assertThat(chats).hasSize(1);
	}

	@DisplayName("해당하는 모임의 가장 최근의 채팅을 보여준다.")
	@Test
	void findFirstByMoimIdOrderByIdDesc() {
		Moim moim = MoimFixture.getCoffeeMoim();
		Moim savedMoim = moimRepository.save(moim);

		Member member = MemberFixture.getHogee();
		memberRepository.save(member);

		Chat hogee1 = ChatFixture.getChatWithMemberAtMoim(member, moim);
		chatRepository.save(hogee1);
		Chat hogee2 = ChatFixture.getChatWithMemberAtMoim(member, moim);
		Chat savedLastChat = chatRepository.save(hogee2);

		Chat chat = chatRepository.findFirstByMoimIdOrderByIdDesc(savedMoim.getId()).get();
		assertThat(chat.getId()).isEqualTo(savedLastChat.getId());
	}
}
