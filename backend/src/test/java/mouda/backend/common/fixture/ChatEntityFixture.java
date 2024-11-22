package mouda.backend.common.fixture;

import java.time.LocalDate;
import java.time.LocalTime;

import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatType;
import mouda.backend.darakbangmember.domain.DarakbangMember;

public class ChatEntityFixture {

	public static ChatEntity getChatEntity(DarakbangMember darakbangMember) {
		return ChatEntity.builder()
			.content("배고파요")
			.chatRoomId(1L)
			.darakbangMember(darakbangMember)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.chatType(ChatType.BASIC)
			.build();
	}
}
