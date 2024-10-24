package mouda.backend.chat.presentation.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import mouda.backend.chat.domain.Author;
import mouda.backend.chat.domain.Chat;
import mouda.backend.chat.domain.ChatOwnership;
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
	public static ChatFindDetailResponse toResponse(ChatOwnership chatOwnership) {
		Chat chat = chatOwnership.getChat();
		return ChatFindDetailResponse.builder()
			.chatId(chat.getId())
			.content(chat.getContent())
			.isMyMessage(chatOwnership.isMine())
			.participation(getParticipantResponse(chatOwnership))
			.date(chat.getDate())
			.time(chat.getTime())
			.chatType(chat.getChatType())
			.build();
	}

	private static ParticipantResponse getParticipantResponse(ChatOwnership chatOwnership) {
		Author author = chatOwnership.getChat().getAuthor();
		return new ParticipantResponse(author.getDarakbangMemberId(), author.getNickname(), author.getProfile());
	}
}
