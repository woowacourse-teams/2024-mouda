package mouda.backend.chat.presentation.response;

import java.util.List;

import mouda.backend.chat.domain.ChatWithAuthor;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
	public static ChatFindUnloadedResponse toResponse(List<ChatWithAuthor> chats) {
		List<ChatFindDetailResponse> responses = chats.stream()
			.map(ChatFindDetailResponse::toResponse)
			.toList();
		return new ChatFindUnloadedResponse(responses);
	}
}
