package mouda.backend.chat.presentation.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import mouda.backend.chat.domain.Author;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatWithAuthor;
import mouda.backend.chat.entity.ChatType;

@Builder
public record ChatFindDetailResponse(
	long chatId,
	String content,
	boolean isMyMessage,
	ParticipantResponse participation,
	LocalDate date,
	LocalTime time,
	ChatType chatType
) {
	public static ChatFindDetailResponse toResponse(ChatWithAuthor chatWithAuthor) {
		Chat chat = chatWithAuthor.getChat();
		return ChatFindDetailResponse.builder()
			.chatId(chat.getId())
			.content(chat.getContent())
			.isMyMessage(chatWithAuthor.isMine())
			.participation(getParticipantResponse(chatWithAuthor))
			.date(chat.getDate())
			.time(chat.getTime())
			.chatType(chat.getChatType())
			.build();
	}

	private static ParticipantResponse getParticipantResponse(ChatWithAuthor chatWithAuthor) {
		Author author = chatWithAuthor.getChat().getAuthor();
		return new ParticipantResponse(author.getNickname(), author.getProfile(), null);
	}
}
