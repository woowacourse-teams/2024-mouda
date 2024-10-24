package mouda.backend.chat.implement;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mouda.backend.chat.domain.ChatPreview;
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

class MoimChatPreviewManagerTest extends DarakbangSetUp {

	@Autowired
	MoimChatPreviewManager moimChatPreviewManager;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	ChamyoRepository chamyoRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@DisplayName("채팅방이 생성된 모임이 없는 경우 빈 리스트를 반환한다.")
	@Test
	void createWithoutBet() {
		// when
		List<ChatPreview> chatPreviews = moimChatPreviewManager.create(darakbangAnna);

		// then
		assertThat(chatPreviews).isEmpty();
	}

	@DisplayName("모임 채팅방 목록을 조회한다.")
	@Test
	void create() {
		// given
		Moim moim1 = MoimFixture.getBasketballMoim(darakbang.getId());
		moim1.openChat();
		Moim moim2 = MoimFixture.getCoffeeMoim(darakbang.getId());
		moimRepository.save(moim1);
		moimRepository.save(moim2);
		chamyoRepository.save(new Chamyo(moim1, darakbangAnna, MoimRole.MOIMER));
		chamyoRepository.save(new Chamyo(moim2, darakbangAnna, MoimRole.MOIMER));

		ChatRoomEntity chatRoom1 = ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim1.getId(), darakbang.getId());
		ChatRoomEntity chatRoom2 = ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim2.getId(), darakbang.getId());
		chatRoomRepository.save(chatRoom1);
		chatRoomRepository.save(chatRoom2);

		// when
		List<ChatPreview> chatPreviews = moimChatPreviewManager.create(darakbangAnna);

		// then
		assertThat(chatPreviews)
			.isNotEmpty()
			.hasSize(1);
	}
}
