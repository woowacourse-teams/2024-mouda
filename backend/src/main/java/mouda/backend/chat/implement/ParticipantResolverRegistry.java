package mouda.backend.chat.implement;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;

@Component
@RequiredArgsConstructor
public class ParticipantResolverRegistry {

	private final List<ParticipantsResolver> participantsResolvers;

	public ParticipantsResolver getResolver(ChatRoomType chatRoomType) {
		return participantsResolvers.stream()
			.filter(participantsResolver -> participantsResolver.support(chatRoomType))
			.findFirst()
			.orElseThrow(IllegalArgumentException::new); // TODO: 예외 처리 리팩토링
	}
}
