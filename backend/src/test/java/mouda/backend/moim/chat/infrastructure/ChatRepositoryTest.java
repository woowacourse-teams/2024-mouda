package mouda.backend.moim.chat.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.ChatFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.ChatRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatRepositoryTest extends DarakbangSetUp {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;

	@DisplayName("모임 아이디가 동일하고 채팅 아이디가 더 큰 채팅 리스트가 조회된다.")
	@Test
	void test() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);

		Chat anna = ChatFixture.getChatWithMemberAtMoim(darakbangAnna, moim);
		chatRepository.save(anna);
		Chat hogee = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		chatRepository.save(hogee);

		List<Chat> chats = chatRepository.findAllUnloadedChats(1L, 1L);

		assertThat(chats).hasSize(1);
	}

	@DisplayName("해당하는 모임의 가장 최근의 채팅을 보여준다.")
	@Test
	void findFirstByMoimIdOrderByIdDesc() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		Chat hogee1 = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		chatRepository.save(hogee1);
		Chat hogee2 = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		Chat savedLastChat = chatRepository.save(hogee2);

		Chat chat = chatRepository.findFirstByMoimIdOrderByIdDesc(savedMoim.getId()).get();
		assertThat(chat.getId()).isEqualTo(savedLastChat.getId());
	}
}
