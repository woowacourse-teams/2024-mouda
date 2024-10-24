package mouda.backend.chat.implement.notification;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatNotificationEvent;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatType;
import mouda.backend.chat.util.ChatDateTimeFormatter;
import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.notification.domain.NotificationPayload;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.implement.NotificationProcessor;

@Component
@RequiredArgsConstructor
public class ChatNotificationEventHandler {

	private final UrlConfig urlConfig;
	private final MoimFinder moimFinder;
	private final BetFinder betFinder;
	private final DarakbangFinder darakbangFinder;
	private final ChatRecipientFinder chatRecipientFinder;
	private final NotificationProcessor notificationProcessor;

	@Async
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(classes = ChatNotificationEvent.class, phase = TransactionPhase.AFTER_COMMIT)
	public void sendChatNotification(
		ChatNotificationEvent chatNotificationEvent
	) {
		long darakbangId = chatNotificationEvent.getDarakbangId();
		ChatRoom chatRoom = chatNotificationEvent.getChatRoom();
		Chat appendedChat = chatNotificationEvent.getAppendedChat();

		ChatRoomType chatRoomType = chatRoom.getType();
		long chatRoomId = chatRoom.getId();

		if (chatRoomType == ChatRoomType.BET) {
			Bet bet = betFinder.find(darakbangId, chatRoom.getTargetId());
			handleBetNotification(bet, appendedChat, chatRoomId);
			return;
		}

		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		handleMoimNotification(moim, appendedChat, chatRoomId);
	}

	private void handleMoimNotification(
		Moim moim, Chat chat, long chatRoomId
	) {
		List<Recipient> recipients = chatRecipientFinder.getMoimChatNotificationRecipients(moim.getId(),
			chat.getAuthor());
		long darakbangId = moim.getDarakbangId();

		processNotification(darakbangId, chatRoomId, moim.getTitle(), chat, recipients);
	}

	private void handleBetNotification(Bet bet, Chat chat, long chatRoomId) {
		List<Recipient> recipients = chatRecipientFinder.getBetChatNotificationRecipients(bet.getId(),
			chat.getAuthor());
		long darakbangId = bet.getDarakbangId();

		processNotification(darakbangId, chatRoomId, bet.getTitle(), chat, recipients);
	}

	private void processNotification(
		long darakbangId, long chatRoomId, String title, Chat chat, List<Recipient> recipients
	) {
		Darakbang darakbang = darakbangFinder.findById(darakbangId);
		ChatNotificationMessage chatNotificationMessage = ChatNotificationMessage.create(darakbang.getName(), title,
			chat);

		NotificationPayload payload = NotificationPayload.createChatPayload(
			chatNotificationMessage.getType(),
			darakbang.getName(),
			chatNotificationMessage.getMessage(),
			urlConfig.getChatRoomUrl(darakbangId, chatRoomId),
			recipients,
			darakbangId,
			chatRoomId
		);

		notificationProcessor.process(payload);
	}

	@Getter
	@RequiredArgsConstructor
	static class ChatNotificationMessage {

		private final String title;
		private final NotificationType type;
		private final String message;

		public static ChatNotificationMessage create(String darakbangName, String title,
			Chat chat) {
			ChatType chatType = chat.getChatType();
			String content = chat.getContent();

			if (chatType == ChatType.PLACE) {
				String message = "'" + title + "'" + " 장소가 '" + content + "' 으로 확정되었어요!";
				return placeConfirmChat(darakbangName, message);
			}
			if (chatType == ChatType.DATETIME) {
				String parsedDateTime = ChatDateTimeFormatter.formatDateTime(content);
				String message = "'" + title + "'" + "시간이 '" + parsedDateTime + "' 으로 확정되었어요!";
				return dateTimeConfirmChat(darakbangName, message);
			}

			String authorNickname = chat.getAuthor().getNickname();
			String message = authorNickname + ": " + content;
			return basicChat(title, message);
		}

		private static ChatNotificationMessage placeConfirmChat(String title, String message) {
			return new ChatNotificationMessage(title, NotificationType.MOIM_PLACE_CONFIRMED,
				message);
		}

		private static ChatNotificationMessage dateTimeConfirmChat(String title,
			String message) {
			return new ChatNotificationMessage(title, NotificationType.MOIM_TIME_CONFIRMED,
				message);
		}

		private static ChatNotificationMessage basicChat(String title, String message) {
			return new ChatNotificationMessage(title, NotificationType.NEW_CHAT, message);
		}
	}
}
