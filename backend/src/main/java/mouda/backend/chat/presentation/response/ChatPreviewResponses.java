package mouda.backend.chat.presentation.response;

import java.util.List;

public record ChatPreviewResponses(
	List<ChatPreviewResponse> chatPreviewResponses
) {
	// public static ChatPreviewResponses toResponse(List<MoimChat> moimChats) {
	// 	List<ChatPreviewResponse> chatPreviews = moimChats.stream()
	// 		.map(ChatPreviewResponse::toResponse)
	// 		.toList();
	// 	return new ChatPreviewResponses(chatPreviews);
	// }

	public static ChatPreviewResponses toResponse(List<ChatPreviewResponse> chatPreviewResponses) {
		return new ChatPreviewResponses(chatPreviewResponses);
	}
}
