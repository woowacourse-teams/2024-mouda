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
import mouda.backend.bet.infrastructure.BetRepository;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.chat.presentation.request.DateTimeConfirmRequest;
import mouda.backend.chat.presentation.request.PlaceConfirmRequest;
import mouda.backend.common.fixture.BetEntityFixture;
import mouda.backend.common.fixture.ChatRoomEntityFixture;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
class ChatServiceTest extends DarakbangSetUp {

	@Autowired
	ChatService chatService;

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

	@DisplayName("장소 확정 채팅에 성공한다.")
	@Test
	void confirmPlace() {
		Moim moim = moimRepository.save(MoimFixture.getSoccerMoim(darakbang.getId()));
		chamyoRepository.save(new Chamyo(moim, darakbangHogee, MoimRole.MOIMER));
		ChatRoomEntity chatRoomEntity = chatRoomRepository.save(
			ChatRoomEntityFixture.getChatRoomEntityOfMoim(moim.getId(), darakbang.getId()));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(chatRoomEntity.getId(), place);

		// when
		chatService.confirmPlace(darakbang.getId(), request, darakbangHogee);

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
		PlaceConfirmRequest request = new PlaceConfirmRequest(chatRoomEntity.getId(), place);

		// when & then
		assertThatThrownBy(() -> chatService.confirmPlace(darakbang.getId(), request, darakbangHogee))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("모임 채팅이 아닙니다.");
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
		DateTimeConfirmRequest request = new DateTimeConfirmRequest(chatRoomEntity.getId(), date, time);

		// when
		chatService.confirmDateTime(darakbang.getId(), request, darakbangHogee);

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
		DateTimeConfirmRequest request = new DateTimeConfirmRequest(chatRoomEntity.getId(), date, time);

		// when & then
		assertThatThrownBy(() -> chatService.confirmDateTime(darakbang.getId(), request, darakbangHogee))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("모임 채팅이 아닙니다.");
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
		PlaceConfirmRequest request = new PlaceConfirmRequest(chatRoomEntity.getId(), place);

		// when & then
		assertThatThrownBy(() -> chatService.confirmPlace(darakbang.getId(), request, darakbangHogee))
			.isInstanceOf(ChatException.class)
			.hasMessage("모이머 권한이 없습니다.");
	}
}
