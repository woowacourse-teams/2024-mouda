package mouda.backend.chat;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import mouda.backend.auth.presentation.controller.AuthController;
import mouda.backend.chat.business.ChatService;
import mouda.backend.chat.domain.Author;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.implement.notification.ChatRecipientFinder;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.chat.infrastructure.ChatRoomRepository;
import mouda.backend.chat.presentation.request.ChatCreateRequest;
import mouda.backend.common.fixture.DarakbangSetUp;
import mouda.backend.common.fixture.MoimFixture;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.domain.MoimRole;
import mouda.backend.moim.infrastructure.ChamyoRepository;
import mouda.backend.moim.infrastructure.MoimRepository;

@SpringBootTest
public class ChatAsyncTest extends DarakbangSetUp {

	@Autowired
	private ChatService chatService;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@MockBean
	private ChatRecipientFinder chatRecipientFinder;

	@Autowired
	private MoimRepository moimRepository;

	@Autowired
	private ChamyoRepository chamyoRepository;

	@Autowired
	private ChatRepository chatRepository;

	private Moim moim;

	@BeforeEach
	void init() {
		moim = moimRepository.save(MoimFixture.getCoffeeMoim(darakbang.getId()));
		chamyoRepository.save(Chamyo.builder()
			.moimRole(MoimRole.MOIMER)
			.darakbangMember(darakbangAnna)
			.moim(moim)
			.build());
	}

	@DisplayName("채팅 알림 전송 과정에서 예외가 발생해도 채팅은 정상적으로 저장된다.")
	@Test
	void createChatAsync() {
		// given
		String content = "비동기 확인 채팅~";
		ChatCreateRequest chatCreateRequest = new ChatCreateRequest(content);
		ChatRoomEntity chatRoom = chatRoomRepository.save(
			new ChatRoomEntity(moim.getId(), darakbang.getId(), ChatRoomType.MOIM));

		// when
		when(chatRecipientFinder.getMoimChatNotificationRecipients(anyLong(), any(Author.class)))
			.thenThrow(new RuntimeException("삐용12"));

		chatService.createChat(darakbang.getId(), chatRoom.getId(), chatCreateRequest, darakbangAnna);

		// then
		List<ChatEntity> chats = chatRepository.findAll();
		assertThat(chats).hasSize(1);

		ChatEntity chat = chats.get(0);
		assertThat(chat.getContent()).isEqualTo(content);
		assertThat(chat.getChatRoomId()).isEqualTo(chatRoom.getId());
	}
}
