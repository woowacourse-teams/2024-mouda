package mouda.backend.chat.implement;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.chat.infrastructure.ChatRepository;
import mouda.backend.darakbangmember.domain.DarakbangMember;
import mouda.backend.moim.domain.ChatType;

@Component
@RequiredArgsConstructor
public class ChatWriter {

	private final ChatRepository chatRepository;

	public void appendPlaceTypeChat(long chatRoomId, String content, DarakbangMember darakbangMember) {
		ChatEntity chatEntity = ChatEntity.builder()
			.chatRoomId(chatRoomId)
			.content(content)
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.PLACE)
			.build();
		chatRepository.save(chatEntity);
	}

	public void appendDateTimeTypeChat(long chatRoomId, LocalDate date, LocalTime time,
		DarakbangMember darakbangMember) {
		ChatEntity chatEntity = ChatEntity.builder()
			.chatRoomId(chatRoomId)
			.content(date.toString() + " " + time.toString())
			.date(LocalDate.now())
			.time(LocalTime.now())
			.darakbangMember(darakbangMember)
			.chatType(ChatType.DATETIME)
			.build();
		chatRepository.save(chatEntity);
	}

	public void append(ChatEntity chatEntity) {
		chatRepository.save(chatEntity);
	}
}
