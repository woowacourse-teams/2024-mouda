package mouda.backend.moim.implement.finder;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.Chats;
import mouda.backend.moim.domain.EmptyChat;
import mouda.backend.moim.exception.ChatErrorMessage;
import mouda.backend.moim.exception.ChatException;
import mouda.backend.moim.infrastructure.ChatRepository;

@Component
@RequiredArgsConstructor
public class ChatFinder {

	private final ChatRepository chatRepository;

	public Chats readAllUnloadedChats(long moimId, long recentChatId) {
		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}
		List<Chat> chats = chatRepository.findAllUnloadedChats(moimId, recentChatId);
		return new Chats(chats);
	}

	public Chat readLastChat(long moimId) {
		return chatRepository.findFirstByMoimIdOrderByIdDesc(moimId)
			.orElse(new EmptyChat(moimId));
	}
}
