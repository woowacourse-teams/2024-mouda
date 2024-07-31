package mouda.backend.chat.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import mouda.backend.chat.domain.Chat;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@DataJpaTest
class ChatRepositoryTest {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("모임 아이디가 동일하고 채팅 아이디가 더 큰 채팅 리스트가 조회된다.")
	@Test
	void test() {
		Moim moim = Moim.builder()
			.place("asdf")
			.maxPeople(1)
			.description("123")
			.time(LocalTime.now())
			.date(LocalDate.now())
			.title("sdfsdfsd")
			.build();
		moimRepository.save(moim);

		Member member = Member.builder()
			.nickname("hogee")
			.build();
		memberRepository.save(member);

		Chat chat1 = Chat.builder()
			.moim(moim)
			.member(member)
			.content("콘텐트")
			.date(LocalDate.now())
			.time(LocalTime.now())
			.build();
		Chat chat2 = Chat.builder()
			.moim(moim)
			.member(member)
			.content("콘텐트")
			.date(LocalDate.now())
			.time(LocalTime.now())
			.build();
		chatRepository.save(chat1);
		chatRepository.save(chat2);

		List<Chat> chats = chatRepository.findAllUnloadedChats(1L, 1L);

		assertThat(chats).hasSize(1);
	}
}
