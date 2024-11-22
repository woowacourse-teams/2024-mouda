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
	@Column(name = "unsubscribed_chats", columnDefinition = "json")
	private List<UnsubscribedChatRooms> unsubscribedChats;

	@Builder
	public SubscriptionEntity(long memberId, List<UnsubscribedChatRooms> unsubscribedChats) {
		this.memberId = memberId;
		this.unsubscribedChats = unsubscribedChats;
		this.moimCreate = true;
	}

	public boolean isSubscribedMoimCreate() {
		return moimCreate;
	}

	public void changeMoimCreateSubscription() {
		this.moimCreate = !this.moimCreate;
	}

	public void changeChatRoomSubscription(long darakbangId, long chatRoomId) {
		for (UnsubscribedChatRooms unsubscribedChatRoom : unsubscribedChats) {
			if (unsubscribedChatRoom.getDarakbangId() == darakbangId) {
				unsubscribedChatRoom.changeChatRoomSubscription(chatRoomId);
				return;
			}
		}
		unsubscribedChats.add(UnsubscribedChatRooms.create(darakbangId, chatRoomId));
	}
}
