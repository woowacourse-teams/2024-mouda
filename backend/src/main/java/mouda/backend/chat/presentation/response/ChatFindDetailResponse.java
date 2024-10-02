package mouda.backend.chat.presentation.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import mouda.backend.chat.domain.ChatWithAuthor;
import mouda.backend.chat.entity.ChatEntity;
import mouda.backend.moim.domain.ChatType;

@Builder
public record ChatFindDetailResponse(
	long chatId,
	String content,
	boolean isMyMessage,
	String nickname,
	LocalDate date,
	LocalTime time,
	ChatType chatType
) {
	public static ChatFindDetailResponse toResponse(ChatWithAuthor chatWithAuthor) {
		ChatEntity chat = chatWithAuthor.getChat();
		return ChatFindDetailResponse.builder()
			.chatId(chat.getId())
			.content(chat.getContent())
			.isMyMessage(chatWithAuthor.isMine())
			.nickname(chat.getDarakbangMember().getNickname())
			.date(chat.getDate())
			.time(chat.getTime())
			.chatType(chat.getChatType())
			.build();
	}
}
