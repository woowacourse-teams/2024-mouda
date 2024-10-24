package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.infrastructure.MoimRepository;

class ChatRoomWriterTest extends DarakbangSetUp {

	@Autowired
	ChatRoomWriter chatRoomWriter;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	BetRepository betRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@DisplayName("새로운 채팅방을 생성한다.")
	@Test
	void append() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		// when
		chatRoomWriter.append(savedMoim.getId(), darakbang.getId(), ChatRoomType.MOIM);

		//then
		Optional<ChatRoomEntity> chatRoomEntity = chatRoomRepository.findByTargetIdAndType(savedMoim.getId(), ChatRoomType.MOIM);
		assertThat(chatRoomEntity.isPresent()).isTrue();
		assertThat(chatRoomEntity.get().getTargetId()).isEqualTo(savedMoim.getId());
		assertThat(chatRoomEntity.get().getType()).isEqualTo(ChatRoomType.MOIM);
	}

	@DisplayName("이미 존재하는 모임 채팅방일 땐 예외를 발생한다.")
	@Test
	void append_alreadyExistedMoimChatRoom() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);
		chatRoomWriter.append(savedMoim.getId(), darakbang.getId(), ChatRoomType.MOIM);

		// when & then
		assertThatThrownBy(() -> chatRoomWriter.append(savedMoim.getId(), darakbang.getId(), ChatRoomType.MOIM))
			.isInstanceOf(ChatException.class);
	}

	@DisplayName("이미 존재하는 배팅 채팅방일 땐 예외를 발생한다.")
	@Test
	void append_alreadyExistedBetChatRoom() {
		// given
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbang.getId(), darakbangAnna.getId());
		BetEntity savedBet = betRepository.save(betEntity);
		chatRoomWriter.append(savedBet.getId(), darakbang.getId(), ChatRoomType.BET);

		// when & then
		assertThatThrownBy(() -> chatRoomWriter.append(savedBet.getId(), darakbang.getId(), ChatRoomType.BET))
			.isInstanceOf(ChatException.class);
	}
}
