package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.domain.Chats;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.ChatEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatFinderTest extends DarakbangSetUp {

	@Autowired
	private ChatRoomFinder chatFinder;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@DisplayName("조회하지 않은 채팅을 조회한다.")
	@Test
	void readAllUnloadedChats() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);

		chatRoomRepository.save(ChatRoomEntityFixture.getChatRoomEntityOfMoim(1L, 1L));

		ChatEntity loadedChat = ChatEntityFixture.getChatEntity(darakbangAnna);
		ChatEntity unloadedChat = ChatEntityFixture.getChatEntity(darakbangHogee);
		chatRepository.save(loadedChat);
		chatRepository.save(unloadedChat);

		Chats chats = chatFinder.findAllUnloadedChats(1L, 1L);

		assertThat(chats.getChats()).isNotEmpty();
	}

	@DisplayName("채팅이 있다면 마지막 채팅의 내용을 조회한다.")
	@Test
	void readLastChatContent() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);

		chatRoomRepository.save(ChatRoomEntityFixture.getChatRoomEntityOfMoim(1L, 1L));

		ChatEntity chatEntity = ChatEntityFixture.getChatEntity(darakbangHogee);
		chatRepository.save(chatEntity);

		ChatRoom chatRoom = chatFinder.readChatRoomByTargetId(moim.getId(), ChatRoomType.MOIM);

		assertThat(chatRoom.getLastChat().getContent()).isEqualTo(chatEntity.getContent());
	}

	@DisplayName("채팅이 없다면 EmptyChat을 반환한다.")
	@Test
	void readLastChatContentWhenChatAbsent() {
		chatRoomRepository.save(ChatRoomEntityFixture.getChatRoomEntityOfMoim(1L, 1L));

		ChatRoom chatRoom = chatFinder.readChatRoomByTargetId(1L, ChatRoomType.MOIM);

		assertThat(chatRoom.getLastChat().getContent()).isEqualTo("");
	}

	@DisplayName("채팅이 있다면 마지막 채팅의 날짜 시간을 조회한다.")
	@Test
	void readLastChatDateTime() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);

		chatRoomRepository.save(ChatRoomEntityFixture.getChatRoomEntityOfMoim(1L, 1L));

		ChatEntity chat1 = ChatEntityFixture.getChatEntity(darakbangAnna);
		ChatEntity chat2 = ChatEntityFixture.getChatEntity(darakbangHogee);
		chatRepository.save(chat1);
		chatRepository.save(chat2);

		ChatRoom chatRoom = chatFinder.readChatRoomByTargetId(moim.getId(), ChatRoomType.MOIM);

		assertThat(chatRoom.getLastChatDateTime().withSecond(0).withNano(0)).isEqualTo(
			LocalDateTime.of(chat2.getDate(), chat2.getTime()).withSecond(0).withNano(0));
	}
}
