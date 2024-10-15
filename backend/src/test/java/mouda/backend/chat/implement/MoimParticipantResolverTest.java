package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.Participant;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class MoimParticipantResolverTest extends DarakbangSetUp {

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MoimParticipantResolver moimParticipantResolver;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@DisplayName("모임 참여자 목록을 반환한다.")
	@Test
	void resolve_shouldReturnParticipantsForGivenChatRoom() {
		// given
		Moim moim = MoimFixture.getCoffeeMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		Chamyo annaChamyo = Chamyo.builder()
			.moim(savedMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build();
		Chamyo hogeeChamyo = Chamyo.builder()
			.moim(savedMoim)
			.darakbangMember(darakbangHogee)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(annaChamyo);
		chamyoRepository.save(hogeeChamyo);

		ChatRoomEntity chatRoomEntity = ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim.getId(), darakbang.getId());
		ChatRoomEntity savedChatRoomEntity = chatRoomRepository.save(chatRoomEntity);
		ChatRoom chatRoom = new ChatRoom(savedChatRoomEntity);

		// when
		List<Participant> participants = moimParticipantResolver.resolve(chatRoom);

		// then
		assertThat(participants).hasSize(2);
		assertThat(participants).extracting("nickname").containsExactlyInAnyOrder("anna", "hogee");
		assertThat(participants).extracting("role").containsExactlyInAnyOrder("MOIMER", "MOIMEE");
	}
}
