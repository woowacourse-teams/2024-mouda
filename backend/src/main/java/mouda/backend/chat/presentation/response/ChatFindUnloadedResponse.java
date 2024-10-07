package mouda.backend.chat.presentation.response;

import java.util.List;

import mouda.backend.chat.domain.ChatWithAuthor;
import mouda.backend.chat.entity.ChatEntity;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
	public static ChatFindUnloadedResponse toResponse(List<ChatEntity> chats, long darabangMemberId) {
		List<ChatFindDetailResponse> responses = chats.stream()
			.map(chat -> new ChatWithAuthor(chat, chat.isMyMessage(darabangMemberId)))
			.map(ChatFindDetailResponse::toResponse)
			.toList();
		return new ChatFindUnloadedResponse(responses);
	}
}
