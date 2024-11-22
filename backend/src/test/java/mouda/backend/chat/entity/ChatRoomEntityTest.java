package mouda.backend.chat.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.ChatRoomEntityFixture;

@SpringBootTest
class ChatRoomEntityTest {

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@DisplayName("targetId와 type이 같은 ChatRoomEntity는 저장할 수 없다.")
	@Test
	void failToCreateDuplicatedUnique() {
		ChatRoomEntity chatRoom = ChatRoomEntityFixture.getChatRoomEntityOfBet(1L, 1L);
		chatRoomRepository.save(chatRoom);

		ChatRoomEntity duplicated = ChatRoomEntityFixture.getChatRoomEntityOfBet(1L, 2L);

		assertThatThrownBy(() -> chatRoomRepository.save(duplicated))
			.isInstanceOf(DataIntegrityViolationException.class);
	}

	@DisplayName("targetId이 같아도 type이 다르다면 ChatRoomEntity는 저장할 수 있다.")
	@Test
	void createWithDifferentType() {
		ChatRoomEntity chatRoom1 = ChatRoomEntityFixture.getChatRoomEntityOfBet(1L, 1L);
		chatRoomRepository.save(chatRoom1);

		ChatRoomEntity chatRoom2 = ChatRoomEntityFixture.getChatRoomEntityOfMoim(1L, 2L);
		chatRoomRepository.save(chatRoom2);

		assertThat(chatRoomRepository.findAll()).hasSize(2);
	}

	@DisplayName("type이 같아도 targetId이 다르다면 ChatRoomEntity는 저장할 수 있다.")
	@Test
	void createWithDifferentTargetId() {
		ChatRoomEntity chatRoom1 = ChatRoomEntityFixture.getChatRoomEntityOfBet(1L, 1L);
		chatRoomRepository.save(chatRoom1);

		ChatRoomEntity chatRoom2 = ChatRoomEntityFixture.getChatRoomEntityOfBet(2L, 2L);
		chatRoomRepository.save(chatRoom2);

		assertThat(chatRoomRepository.findAll()).hasSize(2);
	}
}
