package mouda.backend.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberNotification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private long memberId;

	@Column(nullable = false)
	private long darakbangId;

	@ManyToOne
	@JoinColumn(nullable = false)
	private MoudaNotification moudaNotification;

	@Builder
	public MemberNotification(long memberId, long darakbangId, MoudaNotification moudaNotification) {
		this.memberId = memberId;
		this.darakbangId = darakbangId;
		this.moudaNotification = moudaNotification;
	}
}
