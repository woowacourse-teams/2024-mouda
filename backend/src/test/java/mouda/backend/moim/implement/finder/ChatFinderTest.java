package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.ChatFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.Chats;
import mouda.backend.moim.domain.EmptyChat;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.exception.ChatException;
import mouda.backend.moim.infrastructure.ChatRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatFinderTest extends DarakbangSetUp {

	@Autowired
	private ChatFinder chatFinder;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	MoimRepository moimRepository;

	@DisplayName("조회하지 않은 채팅을 조회한다.")
	@Test
	void readAllUnloadedChats() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);
		Chat loadedChat = ChatFixture.getChatWithMemberAtMoim(darakbangAnna, moim);
		Chat unloadedChat = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		chatRepository.save(loadedChat);
		chatRepository.save(unloadedChat);

		Chats chats = chatFinder.readAllUnloadedChats(1L, 1L);

		assertThat(chats).isNotNull();
	}

	@DisplayName("마지막으로 조회한 채팅ID가 음수라면 예외가 발생한다.")
	@Test
	void failToReadUnloadedChatsWithNegativeLastChatId() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);
		Chat unloadedChat = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		chatRepository.save(unloadedChat);

		assertThatThrownBy(() -> chatFinder.readAllUnloadedChats(1L, -1L))
			.isInstanceOf(ChatException.class);
	}

	@DisplayName("채팅이 있다면 마지막 채팅의 내용을 조회한다.")
	@Test
	void readLastChatContent() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);
		Chat unloadedChat = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		chatRepository.save(unloadedChat);

		Chat lastChat = chatFinder.readLastChat(1L);

		assertThat(lastChat.getContent()).isEqualTo(unloadedChat.getContent());
	}

	@DisplayName("채팅이 없다면 EmptyChat을 반환한다.")
	@Test
	void readLastChatContentWhenChatAbsent() {
		Chat lastChat = chatFinder.readLastChat(1L);

		assertThat(lastChat).isInstanceOf(EmptyChat.class);
	}

	@DisplayName("채팅이 있다면 마지막 채팅의 날짜 시간을 조회한다.")
	@Test
	void readLastChatDateTime() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);
		Chat chat1 = ChatFixture.getChatWithMemberAtMoim(darakbangAnna, moim);
		Chat chat2 = ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim);
		chatRepository.save(chat1);
		chatRepository.save(chat2);

		Chat lastChat = chatFinder.readLastChat(moim.getId());

		assertThat(lastChat.getId()).isEqualTo(chat2.getId());
	}

	@DisplayName("채팅이 없다면 EmptyChat을 반환한다.")
	@Test
	void readLastChatDateTimeWhenChatAbsent() {
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim);

		Chat lastChat = chatFinder.readLastChat(moim.getId());

		assertThat(lastChat).isInstanceOf(EmptyChat.class);
	}
}
