package mouda.backend.chat.implement;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.bet.implement.BetDarakbangMemberWriter;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatRoom;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.entity.ChatType;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.implement.writer.ChamyoWriter;

@Component
@RequiredArgsConstructor
public class ChatWriter {

	private final ChatRepository chatRepository;
	private final ChamyoWriter chamyoWriter;
	private final BetDarakbangMemberWriter betDarakbangMemberWriter;

	public Chat appendPlaceTypeChat(long chatRoomId, String content, DarakbangMember darakbangMember) {
		ChatEntity chatEntity = ChatEntity.builder()
			.chatRoomId(chatRoomId)
			.content(content)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.PLACE)
			.build();
		return chatRepository.save(chatEntity).toChat();
	}

	public Chat appendDateTimeTypeChat(long chatRoomId, LocalDate date, LocalTime time,
		DarakbangMember darakbangMember) {
		ChatEntity chatEntity = ChatEntity.builder()
			.chatRoomId(chatRoomId)
			.content(date.toString() + " " + time.toString())
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.DATETIME)
			.build();
		return chatRepository.save(chatEntity).toChat();
	}

	public Chat append(long chatRoomId, String content, DarakbangMember darakbangMember) {
		ChatEntity chatEntity = ChatEntity.builder()
			.chatRoomId(chatRoomId)
			.content(content)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.BASIC)
			.build();
		return chatRepository.save(chatEntity).toChat();
	}

	public void updateLastReadChat(ChatRoom chatRoom, DarakbangMember darakbangMember, long lastReadChatId) {
		ChatRoomType type = chatRoom.getType();
		if (type == ChatRoomType.MOIM) {
			chamyoWriter.updateLastReadChat(chatRoom.getTargetId(), darakbangMember, lastReadChatId);
		}
		if (type == ChatRoomType.BET) {
			betDarakbangMemberWriter.updateLastReadChat(chatRoom.getTargetId(), darakbangMember, lastReadChatId);
		}
	}
}
