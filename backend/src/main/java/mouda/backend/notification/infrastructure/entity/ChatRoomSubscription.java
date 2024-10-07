package mouda.backend.notification.infrastructure.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomSubscription {

	private long darakbangId;
	private List<Long> chatRoomIds;

	@Override
	public String toString() {
		return "ChatRoomSubscription{" +
			"darakbangId=" + darakbangId +
			", chatRoomIds=" + chatRoomIds +
			'}';
	}

	public boolean isSubscribed(long darakbangId, long chatRoomId) {
		return this.darakbangId == darakbangId && chatRoomIds.contains(chatRoomId);
	}
}
