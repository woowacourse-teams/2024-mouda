package mouda.backend.moim.presentation.response.chat;

import java.util.List;

import mouda.backend.moim.domain.MoimChat;

public record ChatPreviewResponses(
	List<ChatPreviewResponse> chatPreviewResponses
) {
	public static ChatPreviewResponses toResponse(List<MoimChat> moimChats) {
		List<ChatPreviewResponse> chatPreviews = moimChats.stream()
			.map(ChatPreviewResponse::toResponse)
			.toList();
		return new ChatPreviewResponses(chatPreviews);
	}
}
