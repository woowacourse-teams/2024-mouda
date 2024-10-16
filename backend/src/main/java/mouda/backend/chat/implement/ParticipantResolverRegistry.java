package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.exception.ChatErrorMessage;
import mouda.backend.chat.exception.ChatException;

@Component
@RequiredArgsConstructor
public class ParticipantResolverRegistry {

	private final List<ParticipantsResolver> participantsResolvers;

	public ParticipantsResolver getResolver(ChatRoomType chatRoomType) {
		return participantsResolvers.stream()
			.filter(participantsResolver -> participantsResolver.support(chatRoomType))
			.findFirst()
			.orElseThrow(() -> new ChatException(HttpStatus.BAD_REQUEST, ChatErrorMessage.INVALID_CHATROOM_TYPE));
	}
}
