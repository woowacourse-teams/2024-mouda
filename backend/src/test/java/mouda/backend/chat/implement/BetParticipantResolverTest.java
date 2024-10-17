package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.Participant;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;

@SpringBootTest
class BetParticipantResolverTest extends DarakbangSetUp {

	@Autowired
	private BetDarakbangMemberRepository betDarakbangMemberRepository;

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private BetParticipantResolver betParticipantResolver;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@DisplayName("모임 참여자 목록을 반환한다.")
	@Test
	void resolve_shouldReturnParticipantsForGivenChatRoom() {
		// given
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbang.getId(), darakbangAnna.getId());
		BetEntity savedBetEntity = betRepository.save(betEntity);

		BetDarakbangMemberEntity annaEntity = new BetDarakbangMemberEntity(darakbangAnna, savedBetEntity);
		BetDarakbangMemberEntity hogeeEntity = new BetDarakbangMemberEntity(darakbangHogee, savedBetEntity);
		betDarakbangMemberRepository.save(annaEntity);
		betDarakbangMemberRepository.save(hogeeEntity);

		ChatRoomEntity chatRoomEntity = ChatRoomEntityFixture.getChatRoomEntityOfBet(betEntity.getId(),
			darakbang.getId());
		ChatRoomEntity savedChatRoom = chatRoomRepository.save(chatRoomEntity);
		ChatRoom chatRoom = new ChatRoom(savedChatRoom.getId(), savedChatRoom.getTargetId(), savedChatRoom.getType());

		// when
		List<Participant> participants = betParticipantResolver.resolve(chatRoom);

		// then
		assertThat(participants).hasSize(2);
		assertThat(participants).extracting("nickname").containsExactlyInAnyOrder("anna", "hogee");
		assertThat(participants).extracting("role").containsExactlyInAnyOrder("MOIMER", "MOIMEE");
	}
}
