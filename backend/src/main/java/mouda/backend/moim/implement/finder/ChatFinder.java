package mouda.backend.moim.implement.finder;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.infrastructure.ChatRepository;

@Component
@RequiredArgsConstructor
public class ChatFinder {

	private final ChatRepository chatRepository;

	public List<Chat> findAll(long moimId, long recentChatId) {
		return chatRepository.findAllUnloadedChats(moimId, recentChatId);
	}

	public Optional<Chat> findLastChat(long moimId) {
		return chatRepository.findFirstByMoimIdOrderByIdDesc(moimId);
	}

	public String findLastChatContent(long moimId) {
		return findLastChat(moimId)
			.map(Chat::getContent)
			.orElse("");
	}
}
