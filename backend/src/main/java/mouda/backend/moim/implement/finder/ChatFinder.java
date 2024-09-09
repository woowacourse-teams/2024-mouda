package mouda.backend.moim.implement.finder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Chamyo;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.domain.EmptyChat;
import mouda.backend.moim.exception.ChatErrorMessage;
import mouda.backend.moim.exception.ChatException;
import mouda.backend.moim.infrastructure.ChatRepository;

@Component
@RequiredArgsConstructor
public class ChatFinder {

	private final ChatRepository chatRepository;

	public List<Chat> readAllUnloadedChats(long moimId, long recentChatId) {
		if (recentChatId < 0) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_RECENT_CHAT_ID);
		}
		return chatRepository.findAllUnloadedChats(moimId, recentChatId);
	}

	public String readLastChatContent(long moimId) {
		return findLastChat(moimId)
			.map(Chat::getContent)
			.orElse("");
	}

	public LocalDateTime readLastChatDateTime(Chamyo chamyo) {
		return findLastChat(chamyo.getMoim().getId())
			.map(chat -> LocalDateTime.of(chat.getDate(), chat.getTime()))
			.orElse(null);
	}

	public Chat readLastChat(long moimId) {
		return findLastChat(moimId)
			.orElse(new EmptyChat());
	}

	private Optional<Chat> findLastChat(long moimId) {
		return chatRepository.findFirstByMoimIdOrderByIdDesc(moimId);
	}
}
