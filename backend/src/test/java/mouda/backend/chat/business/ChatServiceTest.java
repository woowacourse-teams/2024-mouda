package mouda.backend.chat.business;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.bet.entity.BetEntity;
import mouda.backend.bet.infrastructure.BetDarakbangMemberRepository;
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.LastReadChatRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.chat.presentation.response.ChatFindUnloadedResponse;
import mouda.backend.chat.presentation.response.ChatPreviewResponses;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.ChatEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.exception.ChamyoException;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatServiceTest extends DarakbangSetUp {

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

	@DisplayName("채팅을 생성한다.")
	@Test
	void createChat() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		ChatRoomEntity chatRoom = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(savedMoim.getId(), darakbang.getId()));

		String content = "아 배고파. 오늘 뭐먹지?";
		ChatCreateRequest chatCreateRequest = new ChatCreateRequest(content);

		// when
		chatService.createChat(darakbang.getId(), chatRoom.getId(), chatCreateRequest, darakbangHogee);

		// then
		Optional<ChatEntity> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(content);
	}

	@DisplayName("아무런 채팅이 없는 모임인 경우 빈 값을 반환한다.")
	@Test
	void findUnloadedChatsForFirstTime() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		ChatRoomEntity chatRoom = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(savedMoim.getId(), darakbang.getId()));

		// when
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(1L, 0L, chatRoom.getId(),
			darakbangHogee);

		// then
		assertThat(unloadedChats.chats()).hasSize(0);
	}

	@DisplayName("장소 확정 채팅에 성공한다.")
	@Test
	void confirmPlace() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));
		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim.getId(), darakbang.getId()));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(place);

		// when
		chatService.confirmPlace(darakbang.getId(), chatRoomEntity.getId(), request, darakbangHogee);

		// then
		Optional<ChatEntity> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(place);

		Optional<Moim> moimOptional = moimRepository.findById(moim.getId());
		assertThat(moimOptional.isPresent()).isTrue();
		assertThat(moimOptional.get().getPlace()).isEqualTo(place);
	}

	@DisplayName("모임 채팅이 아닌 경우 장소 확정에 실패한다.")
	@Test
	void failToConfirmPlaceWithBetChat() {
		BetEntity betEntity = betRepository.save(
			BetEntityFixture.getBetEntity(darakbang.getId(), darakbangHogee.getId()));

		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfBet(betEntity.getId(), darakbang.getId()));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(place);

		// when & then
		assertThatThrownBy(
			() -> chatService.confirmPlace(darakbang.getId(), chatRoomEntity.getId(), request, darakbangHogee))
			.isInstanceOf(ChatException.class)
			.hasMessage("잘못된 채팅 방 타입입니다.");
	}

	@DisplayName("날짜 확정 채팅에 성공한다.")
	@Test
	void confirmDateTime() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));
		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim.getId(), darakbang.getId()));

		LocalDate date = LocalDate.now().plusDays(1);
		LocalTime time = LocalTime.now();
		DateTimeConfirmRequest request = new DateTimeConfirmRequest(date, time);

		// when
		chatService.confirmDateTime(darakbang.getId(), chatRoomEntity.getId(), request, darakbangHogee);

		// then
		Optional<ChatEntity> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(date + " " + time);

		Optional<Moim> moimOptional = moimRepository.findById(moim.getId());
		assertThat(moimOptional.isPresent()).isTrue();
		assertThat(moimOptional.get().getDate()).isEqualTo(date);
		assertThat(moimOptional.get().getTime().getHour()).isEqualTo(time.getHour());
		assertThat(moimOptional.get().getTime().getMinute()).isEqualTo(time.getMinute());
	}

	@DisplayName("모임 채팅이 아닌 경우 날짜 확정에 실패한다.")
	@Test
	void failToConfirmDateTimeWithBetChat() {
		BetEntity betEntity = betRepository.save(
			BetEntityFixture.getBetEntity(darakbang.getId(), darakbangHogee.getId()));

		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfBet(betEntity.getId(), darakbang.getId()));

		LocalDate date = LocalDate.now().plusDays(1);
		LocalTime time = LocalTime.now();
		DateTimeConfirmRequest request = new DateTimeConfirmRequest(date, time);

		// when & then
		assertThatThrownBy(
			() -> chatService.confirmDateTime(darakbang.getId(), chatRoomEntity.getId(), request, darakbangHogee))
			.isInstanceOf(ChatException.class)
			.hasMessage("잘못된 채팅 방 타입입니다.");
	}

	@DisplayName("작성자가 아닌 회원이 장소를 확정을 요청하면 실패한다.")
	@Test
	void cannotConfirmPlaceWhenMemberIsNotMoimer() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE));

		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim.getId(), darakbang.getId()));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(place);

		// when & then
		assertThatThrownBy(
			() -> chatService.confirmPlace(darakbang.getId(), chatRoomEntity.getId(), request, darakbangHogee))
			.isInstanceOf(ChamyoException.class)
			.hasMessage("모이머가 아닙니다.");
	}

	@DisplayName("마지막 채팅을 저장한다.")
	@Test
	void updateLastReadChat() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		Chamyo chamyo = chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMEE));

		LastReadChatRequest request = new LastReadChatRequest(1L);

		ChatRoomEntity chatRoomEntity = new ChatRoomEntity(savedMoim.getId(), darakbang.getId(), ChatRoomType.MOIM);
		ChatRoomEntity savedChatRoom = chatRoomRepository.save(chatRoomEntity);

		// when
		chatService.updateLastReadChat(darakbang.getId(), savedChatRoom.getId(), request, darakbangHogee);

		// then
		Optional<Chamyo> optionalChamyo = chamyoRepository.findById(chamyo.getId());
		assertThat(optionalChamyo.isPresent()).isTrue();
		assertThat(optionalChamyo.get().getLastReadChatId()).isEqualTo(1L);
	}

	@DisplayName("최근에 조회한 채팅에서 새롭게 생성된 채팅 내역을 반환한다.")
	@Test
	void findUnloadedChats() {
		// given
		Moim moim = MoimFixture.getSoccerMoim(darakbang.getId());
		Moim savedMoim = moimRepository.save(moim);

		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));

		ChatRoomEntity chatRoom = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(savedMoim.getId(), darakbang.getId()));

		chatRepository.save(ChatEntityFixture.getChatEntity(darakbangHogee));
		chatRepository.save(ChatEntityFixture.getChatEntity(darakbangHogee));
		chatRepository.save(ChatEntityFixture.getChatEntity(darakbangHogee));

		// when
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(1L, 1L, chatRoom.getId(),
			darakbangHogee);

		// then
		assertThat(unloadedChats.chats()).hasSize(2);
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

		ChatPreviewResponses chatPreview = chatRoomService.findChatPreview(darakbangHogee, ChatRoomType.MOIM);
		assertThat(chatPreview.previews()).isEmpty();
	}

	@DisplayName("다락방별 채팅을 조회한다.")
	@Test
	void readDarakbangChatPreview() {
		Moim darakbangMoim = MoimFixture.getSoccerMoim(darakbang.getId());
		moimRepository.save(darakbangMoim);
		chamyoRepository.save(new Chamyo(darakbangMoim, darakbangHogee, MoimRole.MOIMER));
		chatRoomService.openChatRoom(darakbang.getId(), darakbangMoim.getId(), darakbangHogee);
		chatRepository.save(ChatEntityFixture.getChatEntity(darakbangHogee));

		ChatPreviewResponses chatPreview = chatRoomService.findChatPreview(darakbangHogee, ChatRoomType.MOIM);
		assertThat(chatPreview.previews())
			.hasSize(1);

		Moim moudaMoim = MoimFixture.getSoccerMoim(mouda.getId());
		moimRepository.save(moudaMoim);
		chamyoRepository.save(new Chamyo(moudaMoim, moudaHogee, MoimRole.MOIMER));

		ChatPreviewResponses emptyChatPreview = chatRoomService.findChatPreview(moudaHogee, ChatRoomType.MOIM);
		assertThat(emptyChatPreview.previews())
			.hasSize(0);
	}
}
