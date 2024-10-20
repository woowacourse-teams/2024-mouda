package mouda.backend.moim.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mouda.backend.notification.domain.NotificationType;
import mouda.backend.notification.domain.Recipient;

@RequiredArgsConstructor
@Getter
@Builder
public class CommentRecipients {

	private final Map<NotificationType, List<Recipient>> recipients;

	public CommentRecipients() {
		this.recipients = new ConcurrentHashMap<>();
	}

	public void addRecipient(NotificationType notificationType, long memberId, long darakbangMemberId) {
		Recipient recipient = Recipient.builder()
			.memberId(memberId)
			.darakbangMemberId(darakbangMemberId)
			.build();

		if (recipients.containsKey(notificationType)) {
			recipients.get(notificationType).add(recipient);
			return;
		}

		List<Recipient> recipientList = new ArrayList<>();
		recipientList.add(recipient);
		recipients.put(notificationType, recipientList);
	}
}
