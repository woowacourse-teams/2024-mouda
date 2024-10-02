package mouda.backend.notification.infrastructure.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatRoomSubscription {
	private long id;
	private List<Long> chatRoomIds;

	@Override
	public String toString() {
		return "ChatRoomSubscription{" +
			"id=" + id +
			", chatRoomIds=" + chatRoomIds +
			'}';
	}
}
