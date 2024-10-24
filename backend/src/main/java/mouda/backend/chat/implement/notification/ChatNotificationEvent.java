package mouda.backend.chat.implement.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatRoom;

@Getter
@RequiredArgsConstructor
@Builder
public class ChatNotificationEvent {

	private final Long darakbangId;
	private final ChatRoom chatRoom;
	private final Chat appendedChat;
}
