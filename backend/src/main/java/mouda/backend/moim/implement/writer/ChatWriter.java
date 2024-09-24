package mouda.backend.moim.implement.writer;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.moim.domain.Chat;
import mouda.backend.moim.infrastructure.ChatRepository;

@Component
@RequiredArgsConstructor
public class ChatWriter {

	private final ChatRepository chatRepository;

	public Chat save(Chat chat) {
		return chatRepository.save(chat);
	}
}
