package mouda.backend.notification.infrastructure.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UnsubscribedChatRooms {

	private long darakbangId;
	private List<Long> chatRoomIds;

	public static UnsubscribedChatRooms create(long darakbangId, long chatRoomId) {
		List<Long> chatRoomIds = new ArrayList<>();
		chatRoomIds.add(chatRoomId);
		return new UnsubscribedChatRooms(darakbangId, chatRoomIds);
	}

	public void changeChatRoomSubscription(long chatRoomId) {
		if (chatRoomIds.contains(chatRoomId)) {
			chatRoomIds.remove(chatRoomId);
			return;
		}
		chatRoomIds.add(chatRoomId);
	}
}
