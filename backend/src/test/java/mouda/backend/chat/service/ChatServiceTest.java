package mouda.backend.chat.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.dto.request.ChatCreateRequest;
import mouda.backend.chat.dto.request.DateTimeConfirmRequest;
import mouda.backend.chat.dto.request.PlaceConfirmRequest;
import mouda.backend.chat.dto.response.ChatFindUnloadedResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponse;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.repository.ChatRepository;
import mouda.backend.fixture.ChatFixture;
import mouda.backend.fixture.DarakbangSetUp;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class ChatServiceTest extends DarakbangSetUp {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@DisplayName("채팅을 생성한다.")
	@Test
	void createChat() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		String content = "아 배고파. 오늘 뭐먹지?";
		ChatCreateRequest chatCreateRequest = new ChatCreateRequest(1L, content);

		// when
		chatService.createChat(darakbang.getId(), chatCreateRequest, darakbangHogee);

		// then
		Optional<Chat> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(content);
	}

	@DisplayName("아무런 채팅이 없는 모임인 경우 빈 값을 반환한다.")
	@Test
	void findUnloadedChatsForFirstTime() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		// when
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(1L, 0L, moim.getId(), darakbangHogee);

		// then
		assertThat(unloadedChats.chats()).hasSize(0);
	}

	@DisplayName("최근에 조회한 채팅에서 새롭게 생성된 채팅 내역을 반환한다.")
	@Test
	void findUnloadedChats() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim));
		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim));
		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(darakbangHogee, moim));

		// when
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(1L, 1L, moim.getId(), darakbangHogee);

		// then
		assertThat(unloadedChats.chats()).hasSize(2);
	}

	@DisplayName("작성자가 장소를 확정한다.")
	@Test
	void confirmPlace() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(1L, place);

		// when
		chatService.confirmPlace(darakbang.getId(), request, darakbangHogee);

		// then
		Optional<Chat> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(place);

		Optional<Moim> moimOptional = moimRepository.findById(1L);
		assertThat(moimOptional.isPresent()).isTrue();
		assertThat(moimOptional.get().getPlace()).isEqualTo(place);
	}

	@DisplayName("작성자가 아닌 회원이 장소를 확정을 요청하면 실패한다.")
	@Test
	void cannotConfirmPlaceWhenMemberIsNotMoimer() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(1L, place);

		// when & then
		assertThrows(
			ChatException.class,
			() -> chatService.confirmPlace(darakbang.getId(), request, darakbangHogee)
		);
	}

	@DisplayName("작성자가 날짜와 시간을 확정한다.")
	@Test
	void confirmDateTime() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		LocalDate date = LocalDate.now().plusDays(1L);
		LocalTime time = LocalTime.now();
		DateTimeConfirmRequest request = new DateTimeConfirmRequest(1L, date, time);

		// when
		chatService.confirmDateTime(darakbang.getId(), request, darakbangHogee);

		// then
		Optional<Chat> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(date.toString() + " " + time.toString());

		Optional<Moim> moimOptional = moimRepository.findById(1L);
		assertThat(moimOptional.isPresent()).isTrue();
		assertThat(moimOptional.get().getDate()).isEqualTo(date);
		assertThat(moimOptional.get().getTime().getHour()).isEqualTo(time.getHour());
		assertThat(moimOptional.get().getTime().getMinute()).isEqualTo(time.getMinute());
	}

	@DisplayName("열린 채팅방이 없다면 빈 리스트를 반환한다.")
	@Test
	void findChatPreview() {
		Moim basketballMoim = MoimFixture.getBasketballMoim(darakbang.getId());
		moimRepository.save(basketballMoim);

		Chamyo chamyo = Chamyo.builder()
			.darakbangMember(darakbangHogee)
			.moim(basketballMoim)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(chamyo);

		ChatPreviewResponses chatPreview = chatService.findChatPreview(darakbangHogee.getId(), darakbangHogee);
		assertThat(chatPreview.chatPreviewResponses()).isEmpty();
	}

	@DisplayName("다락방별 채팅을 조회한다.")
	@Test
	void readDarakbangChatPreview() {
		Moim darakbangMoim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(darakbangMoim);
		chamyoRepository.save(new Chamyo(darakbangMoim, darakbangHogee, MoimRole.MOIMER));
		chatService.openChatRoom(darakbang.getId(), darakbangMoim.getId(), darakbangHogee);
		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(darakbangHogee, darakbangMoim));

		assertThat(chatService.findChatPreview(darakbangMoim.getId(), darakbangHogee).chatPreviewResponses())
			.hasSize(1);

		Moim moudaMoim = MoimFixture.getSoccerMoim(mouda.getId());
		moimRepository.save(moudaMoim);
		chamyoRepository.save(new Chamyo(moudaMoim, moudaHogee, MoimRole.MOIMER));

		assertThat(chatService.findChatPreview(moudaMoim.getId(), moudaHogee).chatPreviewResponses())
			.hasSize(0);
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

		chatService.openChatRoom(darakbang.getId(), soccerMoim.getId(), darakbangAnna);
		chatService.openChatRoom(darakbang.getId(), coffeeMoim.getId(), darakbangAnna);
		chatService.openChatRoom(darakbang.getId(), basketballMoim.getId(), darakbangAnna);

		chatService.createChat(darakbang.getId(), new ChatCreateRequest(soccerMoim.getId(), "1번 채팅"), darakbangAnna);
		chatService.createChat(darakbang.getId(), new ChatCreateRequest(coffeeMoim.getId(), "2번 채팅"), darakbangAnna);

		List<ChatPreviewResponse> chatPreviewResponses = chatService.findChatPreview(darakbang.getId(), darakbangAnna)
			.chatPreviewResponses();

		assertThat(chatPreviewResponses).extracting(ChatPreviewResponse::lastContent)
			.containsExactly("2번 채팅", "1번 채팅", "");
	}
}
