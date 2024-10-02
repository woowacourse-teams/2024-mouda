package mouda.backend.notification.infrastructure.entity;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
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
@Table(name = "subscription")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long memberId;

	private boolean moimCreate;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "chats", columnDefinition = "json")
	private List<ChatRoomSubscription> chats;

	@Builder
	public SubscriptionEntity(long memberId, boolean moimCreate, List<ChatRoomSubscription> chats) {
		this.memberId = memberId;
		this.moimCreate = moimCreate;
		this.chats = chats;
	}

	@Override
	public String toString() {
		return "SubscriptionEntity{" +
			"id=" + id +
			", memberId=" + memberId +
			", moimCreate=" + moimCreate +
			", chats=" + chats.toString() +
			'}';
	}
}
