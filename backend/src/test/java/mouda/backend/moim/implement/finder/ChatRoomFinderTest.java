package mouda.backend.moim.implement.finder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.common.fixture.ChatFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.ChatRooms;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.ChatRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatRoomFinderTest extends DarakbangSetUp {

	@Autowired
	private ChatRoomFinder chatRoomFinder;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChatRepository chatRepository;

	@DisplayName("최신 채팅 순으로 정렬하여 채팅방을 조회한다. 채팅이 없는 경우 마지막으로 정렬한다.")
	@Test
	void findAllOrderByLastChat() {
		Moim coffeeMoim = MoimFixture.getCoffeeMoim(darakbang.getId());
		coffeeMoim.openChat();
		moimRepository.save(coffeeMoim);
		chamyoRepository.save(new Chamyo(coffeeMoim, darakbangAnna, MoimRole.MOIMER));

		Moim soccerMoim = MoimFixture.getSoccerMoim(darakbang.getId());
		soccerMoim.openChat();
		moimRepository.save(soccerMoim);
		chamyoRepository.save(new Chamyo(soccerMoim, darakbangAnna, MoimRole.MOIMEE));

		Moim basketballMoim = MoimFixture.getBasketballMoim(darakbang.getId());
		basketballMoim.openChat();
		moimRepository.save(basketballMoim);
		chamyoRepository.save(new Chamyo(basketballMoim, darakbangAnna, MoimRole.MOIMEE));

		Chat coffeeChat = ChatFixture.getChatWithMemberAtMoim(darakbangAnna, coffeeMoim);
		chatRepository.save(coffeeChat);

		Chat basketballChat = ChatFixture.getChatWithMemberAtMoim(darakbangAnna, basketballMoim);
		chatRepository.save(basketballChat);

		ChatRooms chatRooms = chatRoomFinder.findAllOrderByLastChat(darakbang.getId(), darakbangAnna);

		assertAll(
			() -> assertThat(chatRooms.getMoimChats().get(0).getLastContent()).isEqualTo(basketballChat.getContent()),
			() -> assertThat(chatRooms.getMoimChats().get(1).getLastContent()).isEqualTo(coffeeChat.getContent()),
			() -> assertThat(chatRooms.getMoimChats().get(2).getLastContent()).isEqualTo("")
		);
	}
}
