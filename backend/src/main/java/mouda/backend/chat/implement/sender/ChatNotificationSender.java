package mouda.backend.chat.implement.sender;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatType;
import mouda.backend.chat.util.ChatDateTimeFormatter;
import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbang.domain.Darakbang;
import mouda.backend.darakbang.implement.DarakbangFinder;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@Component
@EnableConfigurationProperties(UrlConfig.class)
@RequiredArgsConstructor
public class ChatNotificationSender {

	private final UrlConfig urlConfig;
	private final ChatRecipientFinder chatRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;
	private final MoimFinder moimFinder;
	private final BetFinder betFinder;
	private final DarakbangFinder darakbangFinder;

	public void sendChatNotification(
		long darakbangId, ChatRoom chatRoom, Chat appendedChat
	) {
		ChatRoomType chatRoomType = chatRoom.getType();
		long chatRoomId = chatRoom.getId();

		if (chatRoomType == ChatRoomType.BET) {
			Bet bet = betFinder.find(darakbangId, chatRoom.getTargetId());
			sendBetNotification(bet, appendedChat, chatRoomId);
			return;
		}

		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		sendMoimNotification(moim, appendedChat, chatRoomId);
	}

	private void sendMoimNotification(
		Moim moim, Chat chat, long chatRoomId
	) {
		List<Recipient> recipients = chatRecipientFinder.getMoimChatNotificationRecipients(moim.getId(),
			chat.getAuthor());
		long darakbangId = moim.getDarakbangId();

		publishEvent(darakbangId, chatRoomId, moim.getTitle(), chat, recipients);
	}

	private void sendBetNotification(Bet bet, Chat chat, long chatRoomId) {
		List<Recipient> recipients = chatRecipientFinder.getBetChatNotificationRecipients(bet.getId(),
			chat.getAuthor());
		long darakbangId = bet.getDarakbangId();

		publishEvent(darakbangId, chatRoomId, bet.getTitle(), chat, recipients);
	}

	private void publishEvent(long darakbangId, long chatRoomId, String title, Chat chat, List<Recipient> recipients) {
		Darakbang darakbang = darakbangFinder.findById(darakbangId);
		ChatNotification chatNotification = ChatNotification.create(darakbang.getName(), title, chat);

		NotificationEvent notificationEvent = NotificationEvent.chatEvent(
			chatNotification.getType(),
			darakbang.getName(),
			chatNotification.getMessage(),
			urlConfig.getChatRoomUrl(darakbangId, chatRoomId),
			recipients,
			darakbangId,
			chatRoomId
		);

		eventPublisher.publishEvent(notificationEvent);
	}

	@Getter
	@RequiredArgsConstructor
	static class ChatNotification {

		private final String title;
		private final NotificationType type;
		private final String message;

		public static ChatNotification create(String darakbangName, String title, Chat chat) {
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

		private static ChatNotification placeConfirmChat(String title, String message) {
			return new ChatNotification(title, NotificationType.MOIM_PLACE_CONFIRMED, message);
		}

		private static ChatNotification dateTimeConfirmChat(String title, String message) {
			return new ChatNotification(title, NotificationType.MOIM_TIME_CONFIRMED, message);
		}

		private static ChatNotification basicChat(String title, String message) {
			return new ChatNotification(title, NotificationType.NEW_CHAT, message);
		}
	}
}
