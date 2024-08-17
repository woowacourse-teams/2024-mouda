package mouda.backend.chat.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
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
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.repository.ChatRepository;
import mouda.backend.fixture.ChatFixture;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class ChatServiceTest {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@DisplayName("채팅을 생성한다.")
	@Test
	void createChat() {
		// given
		Moim moim = MoimFixture.getSoccerMoim();
		moimRepository.save(moim);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);
		chamyoRepository.save(new Chamyo(moim, member, MoimRole.MOIMER));

		String content = "아 배고파. 오늘 뭐먹지?";
		ChatCreateRequest chatCreateRequest = new ChatCreateRequest(1L, content);

		// when
		chatService.createChat(chatCreateRequest, member);

		// then
		Optional<Chat> chatOptional = chatRepository.findById(1L);
		assertThat(chatOptional.isPresent()).isTrue();
		assertThat(chatOptional.get().getContent()).isEqualTo(content);
	}

	@DisplayName("아무런 채팅이 없는 모임인 경우 빈 값을 반환한다.")
	@Test
	void findUnloadedChatsForFirstTime() {
		// given
		Moim moim = MoimFixture.getSoccerMoim();
		moimRepository.save(moim);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		chamyoRepository.save(new Chamyo(moim, member, MoimRole.MOIMER));

		// when
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(0L, moim.getId(), member);

		// then
		assertThat(unloadedChats.chats()).hasSize(0);
	}

	@DisplayName("최근에 조회한 채팅에서 새롭게 생성된 채팅 내역을 반환한다.")
	@Test
	void findUnloadedChats() {
		// given
		Moim moim = MoimFixture.getSoccerMoim();
		moimRepository.save(moim);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		chamyoRepository.save(new Chamyo(moim, member, MoimRole.MOIMER));

		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(member, moim));
		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(member, moim));
		chatRepository.save(ChatFixture.getChatWithMemberAtMoim(member, moim));

		// when
		ChatFindUnloadedResponse unloadedChats = chatService.findUnloadedChats(1L, moim.getId(), member);

		// then
		assertThat(unloadedChats.chats()).hasSize(2);
	}

	@DisplayName("작성자가 장소를 확정한다.")
	@Test
	void confirmPlace() {
		// given
		Moim moim = MoimFixture.getSoccerMoim();
		moimRepository.save(moim);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		chamyoRepository.save(new Chamyo(moim, member, MoimRole.MOIMER));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(1L, place);

		// when
		chatService.confirmPlace(request, member);

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
		Moim moim = MoimFixture.getSoccerMoim();
		moimRepository.save(moim);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		chamyoRepository.save(new Chamyo(moim, member, MoimRole.MOIMEE));

		String place = "서울시 용산구 강원대로 127-10";
		PlaceConfirmRequest request = new PlaceConfirmRequest(1L, place);

		// when & then
		Assertions.assertThrows(
			ChatException.class,
			() -> chatService.confirmPlace(request, member)
		);
	}

	@DisplayName("작성자가 날짜와 시간을 확정한다.")
	@Test
	void confirmDateTime() {
		// given
		Moim moim = MoimFixture.getSoccerMoim();
		moimRepository.save(moim);

		Member member = MemberFixture.getAnna();
		memberRepository.save(member);

		chamyoRepository.save(new Chamyo(moim, member, MoimRole.MOIMER));

		LocalDate date = LocalDate.now().plusDays(1L);
		LocalTime time = LocalTime.now();
		DateTimeConfirmRequest request = new DateTimeConfirmRequest(1L, date, time);

		// when
		chatService.confirmDateTime(request, member);

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
		Member hogee = MemberFixture.getHogee();
		memberRepository.save(hogee);

		Moim basketballMoim = MoimFixture.getBasketballMoim();
		moimRepository.save(basketballMoim);

		Chamyo chamyo = Chamyo.builder()
			.member(hogee)
			.moim(basketballMoim)
			.moimRole(MoimRole.MOIMEE)
			.build();
		chamyoRepository.save(chamyo);

		ChatPreviewResponses chatPreview = chatService.findChatPreview(hogee);
		assertThat(chatPreview.chatPreviewResponses()).isEmpty();
	}
}
