package mouda.backend.moim.presentation.response.chat;

import java.util.List;

import mouda.backend.moim.domain.ChatWithAuthor;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
	public static ChatFindUnloadedResponse toResponse(List<ChatWithAuthor> chatWithAuthors) {
		List<ChatFindDetailResponse> responses = chatWithAuthors.stream()
			.map(ChatFindDetailResponse::toResponse)
			.toList();
		return new ChatFindUnloadedResponse(responses);
	}
}
