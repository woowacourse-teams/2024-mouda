package mouda.backend.chat.presentation.response;

import java.util.List;

import mouda.backend.chat.domain.ChatOwnership;

public record ChatFindUnloadedResponse(
	List<ChatFindDetailResponse> chats
) {
	public static ChatFindUnloadedResponse toResponse(List<ChatOwnership> chats) {
		List<ChatFindDetailResponse> responses = chats.stream()
			.map(ChatFindDetailResponse::toResponse)
			.toList();
		return new ChatFindUnloadedResponse(responses);
	}
}
