package mouda.backend.chat.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import mouda.backend.chat.domain.Chat;

@Builder
public record ChatFindDetailResponse(
	long chatId,
	String content,
	long memberId,
	String nickname,
	LocalDate date,
	LocalTime time
) {
	public static ChatFindDetailResponse toResponse(Chat chat) {
		return ChatFindDetailResponse.builder()
			.chatId(chat.getId())
			.content(chat.getContent())
			.memberId(chat.getMember().getId())
			.nickname(chat.getMember().getNickname())
			.date(chat.getDate())
			.time(chat.getTime())
			.build();
	}
}
