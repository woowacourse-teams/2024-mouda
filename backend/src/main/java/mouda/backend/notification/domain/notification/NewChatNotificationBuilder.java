package mouda.backend.notification.domain.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.Moim;
import mouda.backend.notification.domain.MoudaNotification;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.NotificationTypeProvider;
import mouda.backend.notification.infrastructure.MoudaNotificationRepository;

@Component
@NotificationTypeProvider(NotificationType.NEW_CHAT)
@RequiredArgsConstructor
public class NewChatNotificationBuilder implements NotificationBuilderStrategy {

	@Value("${url.base}")
	private String baseUrl;

	@Value("${url.chatroom}")
	private String chatroomUrl;

	private final MoudaNotificationRepository moudaNotificationRepository;

	@Override
	public MoudaNotification buildNotification(Long darakbangId, Moim moim, DarakbangMember sender) {
		NotificationType notificationType = NotificationType.NEW_CHAT;
		MoudaNotification notification = MoudaNotification.builder()
			.type(notificationType)
			.body(notificationType.createMessage(sender.getNickname()))
			.targetUrl(baseUrl + String.format(chatroomUrl, darakbangId, moim.getId()))
			.build();

		return moudaNotificationRepository.save(notification);
	}
}
