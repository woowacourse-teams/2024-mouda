package mouda.backend.notification.domain;

import java.util.List;

public class NotificationEvent {

	private final String title;
	private final String body;
	private final List<Recipient> recipients;

	public NotificationEvent(String title, String body, List<Recipient> recipients) {
		this.title = title;
		this.body = body;
		this.recipients = recipients;
	}
}
