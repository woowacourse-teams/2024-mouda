package mouda.backend.common.fixture;

import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatRoomEntity;

public class ChatRoomEntityFixture {

	public static ChatRoomEntity getChatRoomEntityOfMoim(long moimId, long darakbangId) {
		return ChatRoomEntity.builder()
			.targetId(moimId)
			.darakbangId(darakbangId)
			.type(ChatRoomType.MOIM)
			.build();
	}

	public static ChatRoomEntity getChatRoomEntityOfBet(long betId, long darakbangId) {
		return ChatRoomEntity.builder()
			.targetId(betId)
			.darakbangId(darakbangId)
			.type(ChatRoomType.BET)
			.build();
	}
}
