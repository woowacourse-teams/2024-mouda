package mouda.backend.chat.implement;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mouda.backend.chat.domain.ChatRoomType;
import mouda.backend.chat.entity.ChatRoomEntity;
import mouda.backend.chat.infrastructure.ChatRoomRepository;

@Component
@RequiredArgsConstructor
public class ChatRoomWriter {

	private final ChatRoomRepository chatRoomRepository;

	public void append(long targetId, long darakbangId, ChatRoomType chatRoomType) {
		ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
			.targetId(targetId)
			.darakbangId(darakbangId)
			.type(chatRoomType)
			.build();
		chatRoomRepository.save(chatRoomEntity);
	}
}