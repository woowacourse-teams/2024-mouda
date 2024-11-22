package mouda.backend.moim.chat.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.ChatEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatRepositoryTest extends DarakbangSetUp {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@DisplayName("모임 아이디가 동일하고 채팅 아이디가 더 큰 채팅 리스트가 조회된다.")
	@Test
	void test() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);

		ChatEntity anna = ChatEntityFixture.getChatEntity(darakbangAnna);
		chatRepository.save(anna);
		ChatEntity hogee = ChatEntityFixture.getChatEntity(darakbangHogee);
		chatRepository.save(hogee);

		List<ChatEntity> allUnloadedChats = chatRepository.findAllUnloadedChats(1L, 1L);

		assertThat(allUnloadedChats).hasSize(1);
	}

	@DisplayName("해당하는 모임의 가장 최근의 채팅을 보여준다.")
	@Test
	void findFirstByMoimIdOrderByIdDesc() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		ChatEntity hogee1 = ChatEntityFixture.getChatEntity(darakbangHogee);
		chatRepository.save(hogee1);
		ChatEntity hogee2 = ChatEntityFixture.getChatEntity(darakbangHogee);
		ChatEntity lastChat = chatRepository.save(hogee2);

		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(ChatRoomEntityFixture.getChatRoomEntityOfMoim(1L, 1L));

		Optional<ChatEntity> optionalChat = chatRepository.findFirstByChatRoomIdOrderByIdDesc(
			chatRoomEntity.getId());
		assertThat(optionalChat).isPresent();
		assertThat(optionalChat.get().getId()).isEqualTo(lastChat.getId());
	}
}
