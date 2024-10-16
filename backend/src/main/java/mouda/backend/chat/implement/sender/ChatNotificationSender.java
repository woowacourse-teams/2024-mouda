package mouda.backend.chat.implement.sender;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.domain.Bet;
import mouda.backend.bet.implement.BetFinder;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.common.config.UrlConfig;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.moim.implement.finder.ChatRecipientFinder;
import mouda.backend.moim.implement.finder.MoimFinder;
import mouda.backend.notification.domain.NotificationEvent;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;
import mouda.backend.notification.exception.NotificationErrorMessage;
import mouda.backend.notification.exception.NotificationException;

@Component
@EnableConfigurationProperties(UrlConfig.class)
@RequiredArgsConstructor
public class ChatNotificationSender {

	private final UrlConfig urlConfig;
	private final ChatRecipientFinder chatRecipientFinder;
	private final ApplicationEventPublisher eventPublisher;
	private final MoimFinder moimFinder;
	private final BetFinder betFinder;

	public void sendChatNotification(
		long darakbangId, ChatRoom chatRoom, String content, DarakbangMember sender, NotificationType notificationType
	) {
		ChatRoomType chatRoomType = chatRoom.getType();
		long chatRoomId = chatRoom.getId();
		if (chatRoomType == ChatRoomType.BET) {
			Bet bet = betFinder.find(darakbangId, chatRoom.getTargetId());
			sendBetNotification(bet, content, sender, notificationType);
			return;
		}
		Moim moim = moimFinder.read(chatRoom.getTargetId(), darakbangId);
		sendMoimNotification(moim, content, sender, notificationType, chatRoomId);
	}

	private void sendBetNotification(
		Bet bet, String content, DarakbangMember sender, NotificationType notificationType
	) {
		List<Recipient> recipients = chatRecipientFinder.getBetChatNotificationRecipients(bet.getId(), sender);
		long darakbangId = bet.getDarakbangId();
		long chatRoomId = bet.getId();

		publishEvent(notificationType, bet.getTitle(), content, sender, recipients, darakbangId, chatRoomId);
	}

	private void sendMoimNotification(
		Moim moim, String content, DarakbangMember sender, NotificationType notificationType, long chatRoomId
	) {
		List<Recipient> recipients = chatRecipientFinder.getMoimChatNotificationRecipients(moim.getId(), sender);
		long darakbangId = moim.getDarakbangId();

		publishEvent(notificationType, moim.getTitle(), content, sender, recipients, darakbangId, chatRoomId);
	}

	private void publishEvent(
		NotificationType notificationType, String title, String content, DarakbangMember sender,
		List<Recipient> recipients, long darakbangId, long chatRoomId
	) {
		NotificationEvent notificationEvent = NotificationEvent.chatEvent(
			notificationType,
			title,
			ChatNotificationMessage.create(content, sender, notificationType),
			urlConfig.getChatRoomUrl(darakbangId, chatRoomId),
			recipients,
			darakbangId,
			chatRoomId
		);

		eventPublisher.publishEvent(notificationEvent);
	}

	static class ChatNotificationMessage {

		public static String create(String content, DarakbangMember sender, NotificationType type) {
			if (type.isConfirmedType()) {
				return confirmedChatMessage(content, type);
			}
			if (type == NotificationType.NEW_CHAT) {
				return sender.getNickname() + ": " + content;
			}
			throw new NotificationException(
				HttpStatus.BAD_REQUEST, NotificationErrorMessage.NOT_ALLOWED_NOTIFICATION_TYPE
			);
		}

		private static String confirmedChatMessage(String content, NotificationType type) {
			if (type == NotificationType.MOIM_PLACE_CONFIRMED) {
				return "장소가 '" + content + "' 로 확정되었어요!";
			}
			return "시간이 '" + content + "' 로 확정되었어요!";
		}
	}
}
