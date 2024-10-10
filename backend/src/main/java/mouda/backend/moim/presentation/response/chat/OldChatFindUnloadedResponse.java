package mouda.backend.moim.presentation.response.chat;

import java.util.List;

import mouda.backend.moim.domain.ChatWithAuthor;

public record OldChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
	public static OldChatFindUnloadedResponse toResponse(List<ChatWithAuthor> chatWithAuthors) {
		List<ChatFindDetailResponse> responses = chatWithAuthors.stream()
			.map(ChatFindDetailResponse::toResponse)
			.toList();
		return new OldChatFindUnloadedResponse(responses);
	}
}
