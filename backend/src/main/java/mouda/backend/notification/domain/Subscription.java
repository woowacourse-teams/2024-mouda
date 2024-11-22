package mouda.backend.notification.domain;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Subscription {

	private final boolean isSubscribedMoimCreate;
	private final Map<Long, List<Long>> unsubscribedChatRooms;

	public boolean isSubscribedChatRoom(Long darakbangId, Long chatRoomId) {
		if (unsubscribedChatRooms.containsKey(darakbangId)) {
			return !unsubscribedChatRooms.get(darakbangId).contains(chatRoomId);
		}
		return true;
	}

	public boolean isSubscribedMoimCreate() {
		return isSubscribedMoimCreate;
	}
}
