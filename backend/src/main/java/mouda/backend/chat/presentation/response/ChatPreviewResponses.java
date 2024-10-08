package mouda.backend.chat.presentation.response;

import java.util.List;

import mouda.backend.chat.domain.ChatPreview;

public record ChatPreviewResponses(
	List<ChatPreviewResponse> chatPreviewResponses
) {

	public static ChatPreviewResponses toResponse(List<ChatPreview> chatPreviewResponses) {
		List<ChatPreviewResponse> responses = chatPreviewResponses.stream()
			.map(ChatPreviewResponse::toResponse)
			.toList();
		return new ChatPreviewResponses(responses);
	}
}
