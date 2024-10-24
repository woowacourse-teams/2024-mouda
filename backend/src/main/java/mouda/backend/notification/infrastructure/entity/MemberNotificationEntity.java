package mouda.backend.notification.infrastructure.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberNotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long darakbangMemberId;

	private String title;

	private String body;

	private String type;

	private String targetUrl;

	private LocalDateTime createdAt;

	@Builder
	public MemberNotificationEntity(long darakbangMemberId, String title, String body, String type, String targeturl, LocalDateTime createdAt) {
		this.darakbangMemberId = darakbangMemberId;
		this.title = title;
		this.body = body;
		this.type = type;
		this.targetUrl = targeturl;
		this.createdAt = createdAt;
	}
}
