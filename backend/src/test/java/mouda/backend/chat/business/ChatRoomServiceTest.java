package mouda.backend.chat.business;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetDarakbangMemberEntity;
import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.response.ChatPreviewResponse;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.ChatType;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
public class ChatRoomServiceTest extends DarakbangSetUp {

	@Autowired
	ChatService chatService;

	@Autowired
	ChatRoomService chatRoomService;

	@Autowired
	MoimRepository moimRepository;

	@Autowired
	ChamyoRepository chamyoRepository;

	@Autowired
	ChatRoomRepository chatRoomRepository;

	@Autowired
	ChatRepository chatRepository;

	@Autowired
	BetRepository betRepository;

	@Autowired
	BetDarakbangMemberRepository betDarakbangMemberRepository;

	@DisplayName("모임 채팅 미리보기를 조회한다.")
	@Test
	void findMoimChatPreview() {
		// given
		Moim moim = MoimFixture.getBasketballMoim(darakbang.getId());
		moim.openChat();
		Moim savedMoim = moimRepository.save(moim);

		Chamyo chamyo = chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE));
		chamyoRepository.save(chamyo);

		ChatRoomEntity chatRoomEntity = ChatRoomEntityFixture.getChatRoomEntityOfMoim(savedMoim.getId(),
			darakbang.getId());
		ChatRoomEntity chatRoom = chatRoomRepository.save(chatRoomEntity);

		sendChat(chatRoom);

		// when
		ChatPreviewResponses moimChatPreviews = chatRoomService.findChatPreview(darakbangHogee, ChatRoomType.MOIM);

		// then
		assertThat(moimChatPreviews.previews()).hasSize(1);
		assertThat(moimChatPreviews.previews().get(0).lastContent()).isEqualTo("안녕하세요");
		assertThat(moimChatPreviews.previews().get(0).lastReadChatId()).isEqualTo(0);
		assertThat(moimChatPreviews.previews().get(0).participations()).hasSize(1);
	}

	@DisplayName("안내면진다 채팅 미리보기를 조회한다.")
	@Test
	void findBetChatPreview() {
		// given
		BetEntity betEntity = BetEntityFixture.getBetEntity(darakbang.getId(), darakbangHogee.getId());
		BetEntity savedBetEntity = betRepository.save(betEntity);

		BetDarakbangMemberEntity betDarakbangMemberEntity = new BetDarakbangMemberEntity(darakbangHogee,
			savedBetEntity);
		betDarakbangMemberRepository.save(betDarakbangMemberEntity);

		ChatRoomEntity chatRoomEntity = ChatRoomEntityFixture.getChatRoomEntityOfBet(betEntity.getId(),
			darakbang.getId());
		ChatRoomEntity chatRoom = chatRoomRepository.save(chatRoomEntity);

		sendChat(chatRoom);

		// when
		ChatPreviewResponses betChatPreviews = chatRoomService.findChatPreview(darakbangHogee, ChatRoomType.BET);

		// then
		assertThat(betChatPreviews.previews().size()).isEqualTo(1);
		assertThat(betChatPreviews.previews().get(0).lastContent()).isEqualTo("안녕하세요");
		assertThat(betChatPreviews.previews().get(0).lastReadChatId()).isEqualTo(0);
		assertThat(betChatPreviews.previews().get(0).participations()).hasSize(1);
	}

	private void sendChat(ChatRoomEntity savedChatRoom) {
		ChatEntity chatEntity = new ChatEntity("안녕하세요", savedChatRoom.getId(), darakbangHogee, LocalDate.now(),
			LocalTime.now(), ChatType.BASIC);
		chatRepository.save(chatEntity);
	}

	@DisplayName("가장 최근에 생성된 채팅을 기준으로 채팅방 목록을 조회하고, 채팅이 없는 채팅방은 가장 아래에 위치한다.")
	@Test
	void findChatPreview_sortedByLastChatCreatedAt() {
		Moim soccerMoim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		Moim coffeeMoim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		Moim basketballMoim = moimRepository.save(MoimFixture.getBasketballMoim(darakbang.getId()));

		chamyoRepository.save(Chamyo.builder()
			.moim(soccerMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build());

		chamyoRepository.save(Chamyo.builder()
			.moim(coffeeMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build());

		chamyoRepository.save(Chamyo.builder()
			.moim(basketballMoim)
			.darakbangMember(darakbangAnna)
			.moimRole(MoimRole.MOIMER)
			.build());

		chatRoomService.openChatRoom(darakbang.getId(), soccerMoim.getId(), darakbangAnna);
		chatRoomService.openChatRoom(darakbang.getId(), coffeeMoim.getId(), darakbangAnna);
		chatRoomService.openChatRoom(darakbang.getId(), basketballMoim.getId(), darakbangAnna);

		chatService.createChat(darakbang.getId(), 1L, new ChatCreateRequest("1번 채팅"), darakbangAnna);
		chatService.createChat(darakbang.getId(), 2L, new ChatCreateRequest("2번 채팅"), darakbangAnna);

		List<ChatPreviewResponse> chatPreviewResponses = chatRoomService.findChatPreview(darakbangAnna, ChatRoomType.MOIM)
			.previews();

		assertThat(chatPreviewResponses).extracting(ChatPreviewResponse::lastContent)
			.containsExactly("2번 채팅", "1번 채팅", "");
	}
}
