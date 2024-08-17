package mouda.backend.notification.domain;

import java.time.LocalDateTime;

import com.google.firebase.messaging.Notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MoudaNotification {

	private static final String DEFAULT_NOTIFICATION_TITLE = "모우다";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String body;

	@Column(nullable = false)
	private String targetUrl;

	@Column(nullable = false)
	private NotificationType type;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public MoudaNotification(String body, String targetUrl, NotificationType type) {
		this.body = body;
		this.targetUrl = targetUrl;
		this.type = type;
		this.createdAt = LocalDateTime.now();
	}

	public Notification toFcmNotification() {
		return Notification.builder()
			.setTitle(DEFAULT_NOTIFICATION_TITLE)
			.setBody(body)
			.build();
	}
}
