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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String body;

	@Column(nullable = false)
	private String targetUrl;

	@Column(nullable = false)
	private NotificationType type;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Builder
	public MoudaNotification(String title, String body, String targetUrl, NotificationType type) {
		this.title = title;
		this.body = body;
		this.targetUrl = targetUrl;
		this.type = type;
		this.createdAt = LocalDateTime.now();
	}

	public Notification toFcmNotification() {
		return Notification.builder()
			.setTitle(title)
			.setBody(body)
			.build();
	}
}
