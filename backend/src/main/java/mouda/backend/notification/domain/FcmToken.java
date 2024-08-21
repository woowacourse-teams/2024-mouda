package mouda.backend.notification.domain;

import java.time.LocalDateTime;

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
public class FcmToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private long memberId;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	@Builder
	public FcmToken(long memberId, String fcmToken) {
		this.memberId = memberId;
		this.token = fcmToken;
		this.timestamp = LocalDateTime.now();
	}

	public void refreshTimestamp() {
		this.timestamp = LocalDateTime.now();
	}
}
