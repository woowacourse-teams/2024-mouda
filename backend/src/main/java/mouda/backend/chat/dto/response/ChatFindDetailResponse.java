package mouda.backend.chat.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import mouda.backend.chat.domain.Chat;

@Builder
public record ChatFindDetailResponse(
	long chatId,
	String content,
	boolean isMyMessage,
	String nickname,
	LocalDate date,
	LocalTime time,
	boolean isConfirmChat
) {
	public static ChatFindDetailResponse toResponse(Chat chat, boolean isMyMessage) {
		return ChatFindDetailResponse.builder()
			.chatId(chat.getId())
			.content(chat.getContent())
			.isMyMessage(isMyMessage)
			.nickname(chat.getMember().getNickname())
			.date(chat.getDate())
			.time(chat.getTime())
			.isConfirmChat(chat.isConfirmChat())
			.build();
	}
}
