package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatRoomValidatorTest extends DarakbangSetUp {

	@Autowired
	ChatRoomValidator chatRoomValidator;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	BetRepository betRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@DisplayName("이미 존재하는 채팅방인지 검증한다. - ChatRoomType.MOIM")
	@Test
	void existedChatRoom_typeMoim() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		ChatRoomEntity chatRoomEntityOfMoim = ChatRoomEntityFixture.getChatRoomEntityOfMoim(savedMoim.getId(), darakbang.getId());
		chatRoomRepository.save(chatRoomEntityOfMoim);

		// when & then
		assertThatThrownBy(() -> chatRoomValidator.validateAlreadyExists(savedMoim.getId(), ChatRoomType.MOIM))
			.isInstanceOf(ChatException.class);
	}

	@DisplayName("이미 존재하는 채팅방인지 검증한다. - ChatRoomType.BET")
	@Test
	void existedChatRoom_typeBet() {
		// given
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbang.getId(), darakbangAnna.getId());
		BetEntity savedBet = betRepository.save(betEntity);

		ChatRoomEntity chatRoomEntityOfBet = ChatRoomEntityFixture.getChatRoomEntityOfBet(savedBet.getId(), darakbang.getId());
		chatRoomRepository.save(chatRoomEntityOfBet);

		// when & then
		assertThatThrownBy(() -> chatRoomValidator.validateAlreadyExists(savedBet.getId(), ChatRoomType.BET))
			.isInstanceOf(ChatException.class);
	}
}
