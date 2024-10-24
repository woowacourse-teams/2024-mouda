package mouda.backend.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ChatNotificationEvent {

	private final Long darakbangId;
	private final ChatRoom chatRoom;
	private final Chat appendedChat;
}
