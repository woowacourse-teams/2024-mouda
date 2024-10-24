package mouda.backend.chat.implement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;
import mouda.backend.chat.infrastructure.ChatRoomRepository;

@Component
@RequiredArgsConstructor
public class ChatRoomValidator {

	private final ChatRoomRepository chatRoomRepository;

	public void validateAlreadyExists(long targetId, ChatRoomType chatRoomType) {
		if (chatRoomRepository.existsByTargetIdAndType(targetId, chatRoomType)) {
			throw new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.CHATROOM_ALREADY_EXISTS);
		}
	}
}
