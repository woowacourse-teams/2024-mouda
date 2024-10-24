package mouda.backend.chat.implement.notification;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatNotificationEvent;
import mouda.backend.chat.domain.ChatRoom;

@Component
@RequiredArgsConstructor
public class ChatNotificationSender {

	private final ApplicationEventPublisher eventPublisher;

	public void sendChatNotification(long darakbangId, ChatRoom chatRoom, Chat appendedChat) {
		ChatNotificationEvent event = ChatNotificationEvent.builder()
			.darakbangId(darakbangId)
			.chatRoom(chatRoom)
			.appendedChat(appendedChat)
			.build();

		eventPublisher.publishEvent(event);
	}
}
