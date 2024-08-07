package mouda.backend.chat.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mouda.backend.chamyo.domain.Chamyo;
import mouda.backend.chamyo.domain.MoimRole;
import mouda.backend.chamyo.repository.ChamyoRepository;
import mouda.backend.chat.dto.response.ChatPreviewResponses;
import mouda.backend.chat.repository.ChatRepository;
import mouda.backend.config.DatabaseCleaner;
import mouda.backend.fixture.MemberFixture;
import mouda.backend.fixture.MoimFixture;
import mouda.backend.member.domain.Member;
import mouda.backend.member.repository.MemberRepository;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.repository.MoimRepository;

@SpringBootTest
class ChatServiceTest {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private ChatService chatService;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@AfterEach
	void cleanUp() {
		databaseCleaner.cleanUp();
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
